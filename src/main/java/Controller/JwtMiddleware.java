package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import java.util.Map;
import java.util.Set;

public class JwtMiddleware {
    private final TokenManager tokenManager;
    private static final Set<String> PUBLIC_ROUTES = Set.of(
            "/",
            "/test",
            "/login"
    );

    public JwtMiddleware(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

    public void apply(Javalin app) {
        app.before(ctx -> {
            String path = ctx.path();

            if (isPublicRoute(path)) {
                return;
            }

            validateJwt(ctx);
        });

        app.exception(UnauthorizedResponse.class, this::handleUnauthorized);
    }

    private boolean isPublicRoute(String path) {
        return PUBLIC_ROUTES.contains(path);
    }

    private void validateJwt(Context ctx){
        String authHeader = ctx.header("Authorization");
        String idUsuario = ctx.header("Id-Usuario");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).json(Map.of(
                    "success", false,
                    "error", "Authorization header faltante o malformado"
            ));
            throw new UnauthorizedResponse("Authorization header faltante o malformado");
        }

        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            ctx.status(401).json(Map.of(
                    "success", false,
                    "error", "Id-Usuario header requerido"
            ));
            throw new UnauthorizedResponse("Id-Usuario header requerido");
        }

        String token = authHeader.substring(7);

        if (!tokenManager.validateToken(token, idUsuario)) {
            ctx.status(403).json(Map.of(
                    "success", false,
                    "error", "Token invalido o expirado"
            ));
            throw new UnauthorizedResponse("Token invalido o expirado");
        }
    }

    private void handleUnauthorized(UnauthorizedResponse e, Context ctx){
        ctx.status(401).json(Map.of(
                "success", false,
                "error", "Acceso no autorizado: " + e.getMessage()
        ));
    }
}