const Rooms = {
    init: () => {
        document.getElementById('rooms-table-container').innerHTML = `
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h5 class="fw-bold m-0 text-dark"><i class="bi bi-door-open me-2 text-indigo"></i>Catálogo de Habitaciones</h5>
                <span class="badge bg-light text-muted border px-3 py-2 rounded-pill">Disponibles ahora</span>
            </div>
            <div class="row row-cols-1 row-cols-md-2 g-3" id="rooms-grid-content">
                <div class="col-12 text-center text-muted py-4">Cargando catálogo de habitaciones...</div>
            </div>
        `;
        Rooms.loadData();
    },

    loadData: () => {
        fetch(`${API_BASE_URL}/cuartos`, {
            headers: { 'Authorization': sessionStorage.getItem('authHeader') }
        })
        .then(res => res.json())
        .then(data => {
            const grid = document.getElementById('rooms-grid-content');
            if(!grid) return;
            grid.innerHTML = '';
            
            let count = 0;
            data.forEach(cuarto => {
                if (cuarto.disponible) {
                    count++;
                    grid.innerHTML += `
                        <div class="col">
                            <div class="card custom-card h-100 room-card p-3">
                                <div class="d-flex justify-content-between align-items-start mb-2">
                                    <div>
                                        <span class="text-xs text-muted font-monospace d-block" style="font-size:0.75rem">ID COMPONENTE: ${cuarto.id}</span>
                                        <h5 class="fw-bold text-dark mb-0">Habitación ${cuarto.numero}</h5>
                                    </div>
                                    <span class="badge bg-light text-dark border px-2 py-1 rounded small text-uppercase font-monospace" style="font-size:0.75rem">${cuarto.tipo}</span>
                                </div>
                                <p class="text-muted small my-2"><i class="bi bi-moon-stars me-1"></i> Configuración: <strong>${cuarto.numeroCamas} camas</strong></p>
                                <div class="d-flex justify-content-between align-items-center mt-auto pt-2 border-top">
                                    <span class="text-muted small">Por noche</span>
                                    <span class="room-price-tag">$${cuarto.precio} <span class="small fw-normal text-muted" style="font-size:0.75rem">MXN</span></span>
                                </div>
                            </div>
                        </div>
                    `;
                }
            });
            if(count === 0) {
                grid.innerHTML = `<div class="col-12 text-center text-muted">No hay habitaciones disponibles en este momento.</div>`;
            }
        });
    }
};