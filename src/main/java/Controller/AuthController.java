package Controller;

import javax.naming.Context;
import java.util.Map;

public class AuthController {
    public AuthController (TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }
    public void signup (Context ctx){
        Map <String, String> credenciales = ctx.bodyAsClass(Map.class);
        String correo = credenciales.get("correo");
        String contraseña = credenciales.get("contraseña");

        String idUsuario = guardarUser(correo, contraseña);

        String token = tokenManager.issueToken(idUsuario);
        ctx.json(map.of(
                "idUsuario",idUsuario,
                "token", token,
                "success", true
        )):
    }
    public void login (Context ctx) {
        map<String, String> credenciales = ctx.bodyAsClass(Map.class);
        String correo = credenciales.get("correo");
        String contraseña = credenciales.get("contraseña");
        String idUsuario = authenticate(correo, contraseña);
        if (idUsuario != null){

            String token = tokenManager.issueToken(userId);
            ctx.json(map.of(
                    "idUsuario",idUsuario,
                    "token", token,
                    "success", true
            ));
        }
    }
    private  String guardarUser(String correo, String contraseña){
        return "user-" + System.currentTimeMillis();
    }
    private  String authenticate (String correo, String contraseña){
        return  "user-123";
    }

}
