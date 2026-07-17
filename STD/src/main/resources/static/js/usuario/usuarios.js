// @ts-nocheck
function getAbsolutePath() {
    var loc = window.location;
    var pathName = loc.pathname.substring(0, loc.pathname.lastIndexOf('/') + 1);
    return loc.href.substring(0, loc.href.length - ((loc.pathname + loc.search + loc.hash).length - pathName.length));
}
 
var urlBase = getAbsolutePath();
var newUrlBase = urlBase.replace("usuario/formulario", "");
//////console.log("newUrlBase: " + newUrlBase);
 
function buscarEmpleado(empleado) {
    var urlBase = getAbsolutePath();
    //var selectUsuario = $('#selectUsuario');
 
    var busquedaEmpleado = document.getElementById("busquedaEmpleado").value;
    var divEmpleado = document.getElementById("datosUsuario");
    ////console.log("Campo busquedaEmpleado: " + busquedaEmpleado.length);
    var option = '';
    ////console.log("Empleado: " + empleado);
    if (busquedaEmpleado == 0) {
        //console.log("Entro en if de busquedaEmpleado");
        $("select#selectEmpleado").empty();
        $("#EmpleadoId").val('');
        $("#Nombre").val('');
        $("#Puesto").val('');
        divEmpleado.style.display = "none";
 
 
    } else {
        //console.log("Entro en else de busquedaEmpleado");
        var newUrlEmpleado = newUrlBase.replace("usuario/a", "");
        //console.log("url base:" + newUrlEmpleado);
 
 
        $.ajax({
            url: newUrlEmpleado + "empleado/listado?nombre=" + empleado,
            type: "GET",
            dataType: "JSON",
            success: function(respuesta) {
                /*//console.log(respuesta);
                //console.log(respuesta.length);*/
                for (var i = 0; i < respuesta.length; i++) {
                    var nombre = respuesta[i].nombreCompleto;
                    var idUsuario = respuesta[i].empleadoID;
                    /*//console.log()
                    //console.log("nombre : " + nombre);
                    //console.log("idUsuario : " + idUsuario);*/
                    option += '<option value="' + respuesta[i].empleadoID + ' " data-descripcion=' + respuesta[i].descripcion + ' >' + respuesta[i].nombreCompleto + '</option>';
                    $("select#selectEmpleado").empty().append(option);
                    //' data-descripcion='+ respuesta[i].descripcion+'
                }
 
            }
        });
    }
 
}
 
var selectEmpleadoElement = document.getElementById('selectEmpleado');
 
if (selectEmpleadoElement) {
 
    selectEmpleadoElement.onchange = function() {
 
        //console.log("Entro en jquery");
 
        var selectEmpleado = document.getElementById('selectEmpleado').value;
        var divEmpleado = document.getElementById("datosUsuario");
 
        //console.log("SelectEmpleado: " + selectEmpleado);
 
        if (selectEmpleado > 0) {
            //console.log("Entro en mayor a 0");
            divEmpleado.style.display = "block";
        } else {
            //console.log("No tiene valor");
        }
 
        /* Referencia al option seleccionado */
        var mOption = this.options[this.selectedIndex];
 
        /* Referencia a los atributos data de la opción seleccionada */
        var mData = mOption.dataset;
 
        /* Referencia a los input */
        var EmpleadoId = document.getElementById('EmpleadoId');
        var Nombre = document.getElementById('Nombre');
        var Puesto = document.getElementById('Puesto');
 
        //Asignamos el valor del ID Empleado
        EmpleadoId.value = this.value;
 
        //Asignamos el valor del nombre del empleado
        Nombre.value = mOption.text;
 
        //Asignamos el valor del puesto del empleado
        Puesto.value = mData.descripcion;
 
    };
}
 
$(document).ready(function($) {
    $("#edit-form").submit(function(event) {
        event.preventDefault();
        postEdit();
    });
});
 
$(document).ready(function($) {
    $("#editPass-form").submit(function(event) {
        event.preventDefault();
        postEditPass();
    });
});
 
function postEdit() {
    //console.log("Entro en postEdit");
    var something = $('#edit-form').serialize();
    var editUrl = url + 'update';
    $.post(editUrl, something, function(data) {
        updateTable(data);
    });
    clearAndCloseModal('#basicModal');
}
 
function postEditPass() {
    //console.log("Entro en postEdit");
    var something = $('#editPass-form').serialize();
    var editUrl = url + 'update';
    $.post(editUrl, something, function(data) {
        updateTable(data);
    });
    clearAndCloseModal('#basicModalContrasenia');
}
 
function showEditModal(index) {
    var urlBase = getAbsolutePath();
    //console.log("index: " + index);
    var url = newUrlBase + "usuario/loadEntity/";
 
    //console.log("entro en showEditModal");
    //console.log("url: " + url);
 
    var editUrl = url + index;
    //console.log("editUrl: " + editUrl);
    loadEntity(editUrl);
}
 
function modalCambioContra(index) {
    var base = window.location.origin + "/STD/";
    var editUrl = base + "usuario/usuario/cambioContra/" + index;
    loadCambioContra(editUrl);
}
function loadCambioContra(url) {
    $.ajax({
        url: url,
        type: "GET",
        dataType: "JSON",
        success: function(data) {
            populateModalContra(data);
        },
        error: function(xhr) {
            Swal.fire({
                icon: 'warning',
                title: 'No se pudo cargar la información',
                text: xhr.responseText || 'El usuario no tiene histórico de contraseña.',
                confirmButtonText: 'Aceptar'
            });
        }
    });
}
 
function loadEntity(url) {
    //console.log("entro en loadEntity")
    //console.log("url: " + url);
    $.getJSON(url, {}, function(data) {
        populateModal(data);
    });
}
 
function populateModalContra(data) {
    $('#nombre1').val(data.vc_nombre_usuario);
    $('#idUsuario1').val(data.pk_id_usuario);
    $('#password').val('');
}
 
// Assign the data values to the modal form.
function populateModal(data) {
    //console.log("Data en populateModal: " + JSON.stringify(data));
    $('#nombre').val(data.vc_nombre_usuario);
    $('#idUsuario').val(data.pk_id_usuario);
    $('#rol').val(data.rol_idRol.pk_id_rol);
    //$('#update-name').val(data.name);
}
 
function altaUsuario() {
    var newAltaUsu = newUrlBase.replace("usuario/a", "");
    //console.log("newAltaUsu: " + newAltaUsu);
    var nombreUsuario = document.getElementById('nombreUsuario').value;
    var idEmpleado = document.getElementById('EmpleadoId').value;
    var contrasena = document.getElementById('contrasena').value;
    var rol = document.getElementById('roles').value;
    /*//console.log("Nombre Usuario: " + nombreUsuario);
    //console.log("idEmpleado: " + idEmpleado);
    //console.log("contrasena: " + contrasena);
    //console.log("Rol: " + rol);*/
    if (idEmpleado == '') {
        ////console.log("Ingrese un colaborador");
        Swal.fire({
            title: "Precaución",
            text: "Ingrese un colaborador.",
            icon: "info"
        });
    } else if (idEmpleado != '') {
        if (nombreUsuario == '') {
            ////console.log("Ingrese un usuario");
            Swal.fire({
                title: "Precaución",
                text: "Ingrese un nombre de usuario.",
                icon: "info"
            });
        } else {
            ////console.log("Puede continuar");
 
            $.ajax({
                url: newAltaUsu + "usuario/nombre/" + nombreUsuario,
                type: "GET",
                success: function(respuesta) {
                    /*//console.log("Entro en success de get");
                    //console.log("Respuesta: " +respuesta);*/
                    /*//console.log("Validar NombreUsuario: ");
                    //console.log("Respuesta: " + respuesta);*/
                    if (respuesta == true) {
                        Swal.fire({
                            title: "Precaución",
                            text: "Usuario ya registrado, intente con otro nombre.",
                            icon: "info"
                        });
                    } else {
                        ////console.log("Usuario para registrar");
                        $.ajax({
                            url: newAltaUsu + "usuario/idEmpleado/" + idEmpleado,
                            type: "GET",
                            success: function(respuesta) {
                                if (respuesta == true) {
                                    Swal.fire({
                                        title: "Precaución",
                                        text: "Id Empleado ya registrado, intente con otro usuario.",
                                        icon: "info"
                                    });
                                } else {
                                    //console.log("Puede continuar en agregar el usuario");
                                    $.ajax({
                                        url: newAltaUsu + "usuario/guardar_usuario?EmpleadoId=" + idEmpleado + "&nombreUsuario=" + nombreUsuario + "&roles=" + rol,
                                        type: "POST",
                                        success: function() {
                                            //console.log("Entro en success de get");
                                            /*//console.log("Entro en success de get");
                                            //console.log("Respuesta: " +respuesta);*/
                                            //return respuesta
 
                                            //console.log("Busca el usuario registrado con anterioridad");
                                            //console.log("Nombre de usuario: " + nombreUsuario);
                                            $.ajax({
                                                url: newAltaUsu + "usuario/nombreUsuario/" + nombreUsuario,
                                                type: "GET",
                                                dataType: "JSON",
                                                success: function(respuesta) {
                                                    ////console.log("respuesta lenght" + respuesta.lenght);
 
                                                    if (nombreUsuario == '') {
                                                        //console.log("Esta vacio el campo");
                                                    } else {
                                                        if (respuesta != null) {
                                                            var data = {
                                                                dt_vig_cont_fin: (new Date()).toISOString().split('T')[0],
                                                                dt_vig_cont_inicio: (new Date()).toISOString().split('T')[0],
                                                                vc_indicador_activo: 1,
                                                                usr_historico_contrasena_id: respuesta
                                                            };
                                                            //console.log("Data: " + JSON.stringify(data));
                                                            //console.log("Tiene información");
                                                            //console.log("Respuestas");
                                                            //console.log(respuesta);
                                                            //console.log("PK: " + respuesta.pk_id_usuario);
                                                            //console.log("url para hist_contrasena: " + (newAltaUsu + "hist_contrasena/"));
                                                            $.ajax({
                                                                headers: {
                                                                    'Accept': 'application/json',
                                                                    'Content-Type': 'application/json'
                                                                },
                                                                url: newAltaUsu + "hist_contrasena?contra=" + contrasena,
                                                                type: "POST",
                                                                data: JSON.stringify(data),
                                                                dataType: "json",
                                                                success: function(respuesta) {
                                                                    /*//console.log("Entro en success de get");
                                                                    //console.log("Respuesta: " +respuesta);*/
                                                                    //console.log("Se guardo la contraseña en historial contraseña: ");
                                                                    Swal.fire({
                                                                        icon: "success",
                                                                        title: "Registro de usuario exitoso.",
                                                                        showConfirmButton: false
 
                                                                    }).then((result) => {
                                                                        window.location.replace(newAltaUsu + "usuario/formulario");
                                                                    });
 
                                                                },
                                                                error: function(respuesta) {
                                                                    //console.log("Entro en error");
                                                                    //console.log("Respuesta usuario: " + JSON.stringify(respuesta));
                                                                    if (respuesta.status == 500) {
                                                                        Swal.fire({
                                                                            icon: "warning",
                                                                            title: respuesta.status + "No se pudo guardar la contraseña, debido a un fallo de conexión",
                                                                            showConfirmButton: false
 
                                                                        });
                                                                    } else {
                                                                        Swal.fire({
                                                                            icon: "warning",
                                                                            title: respuesta.status,
                                                                            showConfirmButton: false
 
                                                                        });
                                                                    }
 
 
                                                                }
                                                            });
 
                                                        } else {
                                                            //console.log("No tiene información");
                                                        }
                                                    }
 
                                                }
                                            });
 
                                        },
                                        error: function(respuesta) {
                                            //console.log("Entro en error");
                                            Swal.fire({
                                                icon: "warning",
                                                title: "No se pudo agregar el usuario",
                                                showConfirmButton: false
 
                                            });
 
                                        }
                                    });
                                }
 
                            },
                            error: function(respuesta) {
                                //console.log("Entro en error");
                                Swal.fire({
                                    icon: 'warning',
                                    title: respuesta,
                                    showConfirmButton: false
 
                                });
 
                            }
                        });
                    }
 
                },
                error: function(respuesta) {
                    //console.log("Entro en error");
                    //console.log("Usu: " + JSON.stringify(respuesta));
                    Swal.fire({
                        icon: 'warning',
                        title: respuesta,
                        showConfirmButton: false
 
                    });
 
                }
            });
 
        }
    }
 
}
 
function validarNombreUsuario(nombreUsuario) {
    //var urlBase = getAbsolutePath();
    $.ajax({
        url: newUrlBase + "usuario/nombre/" + nombreUsuario,
        type: "GET",
        success: function(respuesta) {
            /*//console.log("Entro en success de get");
            //console.log("Respuesta: " +respuesta);*/
            //console.log("Validar NombreUsuario: ");
            //console.log("Respuesta: " + respuesta);
            return respuesta
 
        },
        error: function(respuesta) {
            //console.log("Entro en error");
            Swal.fire({
                icon: 'warning',
                title: respuesta,
                showConfirmButton: false
 
            });
 
        }
    });
}
 
//funcion para dar de baja al usuario (Estatus Usuario == false)
function bajaUsuario(usuario) {
    //console.log("Entro en dar baja usuario");
    //console.log("Usuario: " + usuario);
    var urlBase = getAbsolutePath();
    $.ajax({
        url: newUrlBase + "baja_usuario?usuario=" + usuario,
        type: "POST",
        success: function() {
            /*//console.log("Entro en success de get");
            //console.log("Respuesta: " +respuesta);*/
            //console.log("Validar NombreUsuario: ");
            Swal.fire({
                icon: 'success',
                title: "Baja de usuario correctamente.",
                showConfirmButton: false
 
            }).then((result) => {
                window.location.reload();
            });
 
        },
        error: function() {
            //console.log("Entro en error");
            Swal.fire({
                icon: 'warning',
                title: "No se realizo la baja del usuario",
                showConfirmButton: false
 
            }).then((result) => {
                window.location.reload();
            });
 
        }
    });
}
 
//funcion para reacitvar el usuario (Estatus Usuario = true)
function reactivarUsuario(usuario) {
    var urlBase = getAbsolutePath();
    //console.log("Entro en reactivar usuario");
    //console.log("Usuario: " + usuario);
    $.ajax({
        url: newUrlBase + "reactivar_usuario?usuario=" + usuario,
        type: "POST",
        success: function() {
            /*//console.log("Entro en success de get");
            //console.log("Respuesta: " +respuesta);*/
            //console.log("Validar NombreUsuario: ");
 
            Swal.fire({
                icon: 'success',
                title: "Reactivación de usuario correctamente.",
                showConfirmButton: false
 
            }).then((result) => {
                window.location.reload();
            });
 
        },
        error: function() {
            //console.log("Entro en error");
            Swal.fire({
                icon: 'warning',
                title: "No se reactivo el usuario",
                showConfirmButton: false
 
            }).then((result) => {
                window.location.reload();
            });
 
        }
    });
}
 
//función para editar usuarios desde el modal
function editarUsuario() {
 
    var idUsuario = document.getElementById('idUsuario').value;
    var nombreUsuario = document.getElementById('nombre').value;
    var rol = document.getElementById('rol').value;
    //var urlBase = getAbsolutePath();
    //Primero validamos que el nombre de usuario no exista.
    if (nombreUsuario == "") {
        //console.log("Ingresa un nombre de usuario");
        Swal.fire({
            icon: 'warning',
            title: "El campo de nombre de usuario está vacío, ingresa un nombre",
            showConfirmButton: false
 
        });
    } else {
        //console.log("Puede continuar");
        $.ajax({
            url: newUrlBase + "editar_usuario?usuario=" + nombreUsuario +
                "&idUsuario=" + idUsuario + "&rol=" + rol,
            type: "POST",
            success: function() {
                /*//console.log("Entro en success de get");
                //console.log("Respuesta: " +respuesta);*/
 
 
                Swal.fire({
                    icon: 'success',
                    title: "Se cambio correctamente el usuario: " + nombreUsuario,
                    showConfirmButton: false
 
                }).then((result) => {
                    window.location.reload();
                });
 
            },
            error: function() {
                ////console.log("Entro en error");
                Swal.fire({
                    icon: 'warning',
                    title: "No se realizo los cambios del usuario, favor de validar los datos ingresados. ",
                    showConfirmButton: false
 
                }).then((result) => {
                    window.location.reload();
                });
 
            }
        });
    }
 
}
 
function cambiarContrasenia() {
    var idUsuario = document.getElementById('idUsuario1').value;
    var contrasenia = document.getElementById('password').value;
 
    if (contrasenia == "") {
        Swal.fire({
            icon: 'warning',
            title: "El campo de contraseña nueva está vacío, ingresa una contraseña",
            showConfirmButton: false
        });
        return;
    }
 
    $.ajax({
        url: newUrlBase + "mensajeContrasena?contrasena=" + encodeURIComponent(contrasenia),
        type: "GET",
        success: function(respuesta) {
 
            if (respuesta != "Si cumple con los parametros requeridos") {
                Swal.fire({
                    icon: 'warning',
                    title: respuesta,
                    showConfirmButton: false
                });
                return;
            }
 
            $.ajax({
                url: newUrlBase + "guardarCambioContra?idUsuario=" + idUsuario +
                    "&contrasena=" + encodeURIComponent(contrasenia),
                type: "POST",
                success: function() {
                    Swal.fire({
                        icon: 'success',
                        title: 'Contraseña cambiada correctamente',
                        showConfirmButton: false
                    }).then(() => {
                        window.location.replace(newUrlBase + "formulario");
                    });
                },
                error: function(xhr) {
                    Swal.fire({
                        icon: 'warning',
                        title: xhr.responseText || "Error al cambiar la contraseña",
                        showConfirmButton: false
                    });
                }
            });
        },
        error: function() {
            Swal.fire({
                icon: 'warning',
                title: "Error al validar la contraseña",
                showConfirmButton: false
            });
        }
    });
}
 
 
function reactivarEstatusUsuario(usuario) {
 
    $.ajax({
        url: urlBase + "reactivar_usuario_sesion/" + usuario,
        type: "POST",
        success: function() {
            Swal.fire({
                icon: 'success',
                title: 'Desbloqueo correctamente',
                showConfirmButton: false,
                timer: 24000 // es ms (mili-segundos)
            }).then((result) => {
                window.location.reload();
            });
 
        },
        error: function() {
            Swal.fire({
                icon: 'warning',
                title: 'No se realizo correctamente el desbloqueo',
                showConfirmButton: false
                //timer: 24000 // es ms (mili-segundos)
            });
        }
    });
    
}