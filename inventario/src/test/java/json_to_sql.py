import json

with open('c:/Users/User/Documents/GitHub/StockGOZU/inventario/src/test/java/datos.json', 'r') as file:
    data = json.load(file)

sql_statements = []

for sucursal in data['sucursales']:
    sql_statements.append(
        f"INSERT INTO sucursal (id, nombre, ubicacion) VALUES ({sucursal['id']}, '{sucursal['nombre']}', '{sucursal['ubicacion']}');"
    )

# Insertar productos
for producto in data['productos']:
    sql_statements.append(
        f"INSERT INTO producto (id, nombre, descripcion, codigo, categoria, unidad_medida, precio_compra, precio_venta) "
        f"VALUES ({producto['id']}, '{producto['nombre']}', '{producto['descripcion']}', '{producto['codigo']}', "
        f"'{producto['categoria']}', '{producto['unidadMedida']}', {producto['precioCompra']}, {producto['precioVenta']});"
    )

# Insertar inventario
for inventario in data['inventario']:
    sql_statements.append(
        f"INSERT INTO inventario (sucursal_id, producto_id, cantidad, stock_minimo) "
        f"VALUES ({inventario['sucursalId']}, {inventario['productoId']}, {inventario['cantidad']}, {inventario['stockMinimo']});"
    )

# Insertar usuarios
for usuario in data['usuarios']:
    sql_statements.append(
        f"INSERT INTO usuario (id, nombre, username, password_hash, rol) "
        f"VALUES ({usuario['id']}, '{usuario['nombre']}', '{usuario['username']}', '{usuario['passwordHash']}', '{usuario['rol']}');"
    )

# Guardar las sentencias SQL en un archivo
with open('datos.sql', 'w') as file:
    file.write('\n'.join(sql_statements))

print("Archivo SQL generado: datos.sql")