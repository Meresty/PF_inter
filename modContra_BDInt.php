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

if (!isset($data["usuario"]) || !isset($data["nuevaContrasena"])) {
    echo json_encode(["success" => false, "message" => "Faltan datos para actualizar la contraseña"]);
    exit();
}

$usuario = $data["usuario"];
$nuevaContrasena = $data["nuevaContrasena"];

// Verificar si el usuario existe
$stmt = $pdo->prepare("SELECT * FROM usuarios WHERE usuario = :usuario");
$stmt->bindParam(":usuario", $usuario);
$stmt->execute();

if ($stmt->rowCount() == 1) {
    // Actualizar la contraseña
    $stmt = $pdo->prepare("UPDATE usuarios SET contrasena = :nuevaContrasena WHERE usuario = :usuario");
    $stmt->bindParam(":usuario", $usuario);
    $stmt->bindParam(":nuevaContrasena", $nuevaContrasena);

    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "Contraseña actualizada exitosamente"]);
    } else {
        echo json_encode(["success" => false, "message" => "Error al actualizar la contraseña"]);
    }
} else {
    echo json_encode(["success" => false, "message" => "Usuario no encontrado"]);
}
?>