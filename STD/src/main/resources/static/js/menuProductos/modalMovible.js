 $(document).ready(function(){
    // Obtén la modal por su ID
    var modal = $("#altaPlantilla");

    // Obtén el encabezado de la modal (donde el usuario puede hacer clic para arrastrar)
    var modalHeader = modal.find(".modal-content");

    // Inicializa las variables para el seguimiento del arrastre
    var isDragging = false;
    var offset = {x: 0, y: 0};

    // Escucha el clic en el encabezado para comenzar el arrastre
    modalHeader.mousedown(function(event){
      isDragging = true;
      offset = {
        x: event.clientX - modal[0].getBoundingClientRect().left,
        y: event.clientY - modal[0].getBoundingClientRect().top
      };
    });

    // Escucha el movimiento del ratón para arrastrar la modal
    $(document).mousemove(function(event){
      if(isDragging){
        modal.offset({
          top: event.clientY - offset.y,
          left: event.clientX - offset.x
        });
      }
    });

    // Escucha el soltar del ratón para detener el arrastre
    $(document).mouseup(function(){
      isDragging = false;
    });
  });