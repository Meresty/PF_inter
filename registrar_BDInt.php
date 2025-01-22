<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");

// Conexión a la base de datos
$host = "localhost";
$dbname = "BD_Integrador";
$username = "root";
$password = "";

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Error al conectar a la base de datos"]);
    exit();
}

// Obtener los datos enviados
$data = json_decode(file_get_contents("php://input"), true);

if (isset($data["action"])) {
    $action = $data["action"];
} else {
    // Si no se especifica una acción, asumir que es un intento de login
    $action = "register";
}

switch ($action) {
    case "login":
        if (!isset($data["usuario"]) || !isset($data["contrasena"])) {
            echo json_encode(["success" => false, "message" => "Faltan datos para iniciar sesión"]);
            exit();
        }

        $usuario = $data["usuario"];
        $contrasena = $data["contrasena"];

        // Verificar si el usuario existe
        $stmt = $pdo->prepare("SELECT * FROM usuarios WHERE usuario = :usuario");
        $stmt->bindParam(":usuario", $usuario);
        $stmt->execute();

        if ($stmt->rowCount() == 1) {
            $user = $stmt->fetch(PDO::FETCH_ASSOC);

            // Verificar la contraseña
            if ($contrasena === $user["contrasena"]) {
                echo json_encode(["success" => true, "message" => "Login exitoso", "id" => $user["id"]]);
            } else {
                echo json_encode(["success" => false, "message" => "Contraseña incorrecta"]);
            }
        } else {
            echo json_encode(["success" => false, "message" => "Usuario no encontrado"]);
        }
        break;

    case "register":
        if (!isset($data["usuario"]) || !isset($data["contrasena"])) {
            echo json_encode(["success" => false, "message" => "Faltan datos para registrar usuario"]);
            exit();
        }

        $usuario = $data["usuario"];
        $contrasena = $data["contrasena"];

        // Verificar si el usuario ya existe
        $stmt = $pdo->prepare("SELECT * FROM usuarios WHERE usuario = :usuario");
        $stmt->bindParam(":usuario", $usuario);
        $stmt->execute();

        if ($stmt->rowCount() > 0) {
            echo json_encode(["success" => false, "message" => "El usuario ya existe"]);
        } else {
            // Insertar el nuevo usuario
            $stmt = $pdo->prepare("INSERT INTO usuarios (usuario, contrasena) VALUES (:usuario, :contrasena)");
            $stmt->bindParam(":usuario", $usuario);
            $stmt->bindParam(":contrasena", $contrasena);

            if ($stmt->execute()) {
                echo json_encode(["success" => true, "message" => "Usuario registrado exitosamente"]);
            } else {
                echo json_encode(["success" => false, "message" => "Error al registrar usuario"]);
            }
        }
        break;

    case "update_password":
        if (!isset($data["usuario"]) || !isset($data["nueva_contrasena"])) {
            echo json_encode(["success" => false, "message" => "Faltan datos para actualizar contraseña"]);
            exit();
        }

        $usuario = $data["usuario"];
        $nueva_contrasena = $data["nueva_contrasena"];

        // Verificar si el usuario existe
        $stmt = $pdo->prepare("SELECT * FROM usuarios WHERE usuario = :usuario");
        $stmt->bindParam(":usuario", $usuario);
        $stmt->execute();

        if ($stmt->rowCount() == 1) {
            // Actualizar la contraseña
            $stmt = $pdo->prepare("UPDATE usuarios SET contrasena = :nueva_contrasena WHERE usuario = :usuario");
            $stmt->bindParam(":usuario", $usuario);
            $stmt->bindParam(":nueva_contrasena", $nueva_contrasena);

            if ($stmt->execute()) {
                echo json_encode(["success" => true, "message" => "Contraseña actualizada exitosamente"]);
            } else {
                echo json_encode(["success" => false, "message" => "Error al actualizar la contraseña"]);
            }
        } else {
            echo json_encode(["success" => false, "message" => "Usuario no encontrado"]);
        }
        break;

    default:
        echo json_encode(["success" => false, "message" => "Acción no válida"]);
        break;
}
?>