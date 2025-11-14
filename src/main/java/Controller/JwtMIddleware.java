package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

import java.util.Map;

public class JwtMIddleware {
    private final   TokenManager tokenManager;
    public  JwtMIddleware(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

    public  void  apply (Javalin app) {
        app.before("/api/protected/*", this::validateJwt);
        app.before("usuarios", this :: validateJwt);
    }
    private  void validateJwt(context ctx){
        String authHeader = ctx.header("Autorizacion");
        String idUsuario = ctx.header("Id.Usuario");
        if (authHeader == null || !authHeader.startsWith("bearer")){
            ctx.status(401).json(Map.of(
                    "error", "Authorization header faltante o malformado"
            ));
            return;
        }
        if (idUsuario == null){
            ctx.status(401).json(Map.of(
                    "error", "User-Id header requerido"
            ));

        }
        String token = authHeader.substring(7);
        try {
            if (!tokenManager.validateToken(token.idUsuario)){
                ctx.status(403).json(Map.of(
                        "error", "Error: Token invalido o expirado");
                ));
                throw new Exception("Token invalido o expirado");
            }
        }catch (Exception e) {
                ctx.status(401).json(Map.of(
                        "error", "error al validar token"

                ));
            }
            int status = ctx.statuscode();
            if (status == 401 || status == 403){
                throw  new UnauthorizedResponse("eror: " + ctx.result());
            }
        }
        public void noAutorizo(UnauthorizedResponse e, Context ctx){
        ctx.status(401).json(Map.of( "error", "Acceso no autorizado: "+e.getMessage()));
    }
}
