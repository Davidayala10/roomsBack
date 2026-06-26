const Reservations = {
    init: () => {
        const formContainer = document.getElementById('reservation-form-container');
        const tableContainer = document.getElementById('reservations-table-container');

        // 1. Renderizar Formulario SOLO si el contenedor existe en la pestaña actual
        if (formContainer) {
            formContainer.innerHTML = `
                <div class="custom-card p-4 bg-white sticky-md-top" style="top: 90px; z-index:10;">
                    <h5 class="fw-bold mb-3 text-dark"><i class="bi bi-calendar-plus me-2 text-primary"></i>Nueva Reservación</h5>
                    <form onsubmit="Reservations.handleSubmit(event)">
                        <div class="mb-3">
                            <label class="form-label fw-medium small text-secondary">ID de la Habitación</label>
                            <input type="number" id="resCuartoId" class="form-control bg-light" required placeholder="Ej. 1">
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-medium small text-secondary">Fecha de Entrada</label>
                            <input type="date" id="resFechaInicio" class="form-control bg-light" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-medium small text-secondary">Fecha de Salida</label>
                            <input type="date" id="resFechaFin" class="form-control bg-light" required>
                        </div>
                        <button type="submit" class="btn btn-gradient-primary w-100 fw-bold py-2 mt-2 shadow-sm">
                            <i class="bi bi-check-circle me-1"></i> Generar Reservación
                        </button>
                    </form>
                </div>
            `;
        }

        // 2. Renderizar Historial SOLO si el contenedor existe en la pestaña actual
        if (tableContainer) {
            tableContainer.innerHTML = `
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h5 class="fw-bold m-0 text-dark"><i class="bi bi-clock-history me-2 text-info"></i>Historial de Control</h5>
                </div>
                <div class="custom-card bg-white p-3">
                    <div class="table-responsive">
                        <table class="table modern-table table-hover align-middle mb-0">
                            <thead>
                                <tr>
                                    <th>Folio</th>
                                    <th>Habitación</th>
                                    <th>Entrada</th>
                                    <th>Salida</th>
                                    <th>Total</th>
                                    <th>Estatus</th>
                                    <th class="text-end">Acción</th>
                                </tr>
                            </thead>
                            <tbody id="reservations-tbody">
                                <tr><td colspan="7" class="text-center text-muted">Cargando historial...</td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            `;
            // Mandamos a llamar los datos del servidor una vez que la tabla ya existe en el DOM
            Reservations.loadData();
        }
    },

    loadData: () => {
        fetch(`${API_BASE_URL}/reservaciones`, {
            headers: { 'Authorization': sessionStorage.getItem('authHeader') }
        })
        .then(res => res.json())
        .then(data => {
            const tbody = document.getElementById('reservations-tbody');
            if(!tbody) return; // Salvaguarda por si el componente se desmontó rápido
            tbody.innerHTML = '';
            
            data.forEach(reserva => {
                const esActiva = reserva.estado === 'CONFIRMADA';
                const badgeClass = esActiva ? 'bg-success-subtle text-success border border-success' : 'bg-danger-subtle text-danger border border-danger';
                const accionBtn = esActiva 
                    ? `<button class="btn btn-outline-danger btn-sm rounded-pill px-3" onclick="Reservations.cancel(${reserva.id})">Cancelar</button>`
                    : `<span class="text-muted small">—</span>`;

                tbody.innerHTML += `
                    <tr>
                        <td><span class="fw-bold text-dark">#${reserva.id}</span></td>
                        <td><span class="badge bg-light text-dark border">Cuarto ${reserva.idCuarto}</span></td>
                        <td><span class="text-secondary small"><i class="bi bi-calendar-event me-1"></i>${reserva.fechaInicio}</span></td>
                        <td><span class="text-secondary small"><i class="bi bi-calendar-event me-1"></i>${reserva.fechaFin}</span></td>
                        <td><span class="fw-bold text-dark">$${reserva.costoTotal}</span></td>
                        <td><span class="badge ${badgeClass} rounded-pill px-2.5 py-1 text-xs font-monospace">${reserva.estado}</span></td>
                        <td class="text-end">${accionBtn}</td>
                    </tr>
                `;
            });
            if(data.length === 0) {
                tbody.innerHTML = `<tr><td colspan="7" class="text-center text-muted py-4">No se han registrado reservaciones previas.</td></tr>`;
            }
        });
    },

    handleSubmit: (event) => {
        event.preventDefault();
        const payload = {
            idCuarto: parseInt(document.getElementById('resCuartoId').value),
            fechaInicio: document.getElementById('resFechaInicio').value,
            fechaFin: document.getElementById('resFechaFin').value
        };

        fetch(`${API_BASE_URL}/reservaciones`, {
            method: 'POST',
            headers: {
                'Authorization': sessionStorage.getItem('authHeader'),
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
        .then(res => {
            if (res.ok) {
                alert('¡Reservación creada exitosamente!');
                App.switchTab('rooms');
            } else {
                return res.json().then(err => { alert(`Validación: ${err.message || 'Error en fechas'}`); });
            }
        });
    },

    cancel: (id) => {
        if (confirm('¿Confirmas la cancelación de esta reservación?')) {
            fetch(`${API_BASE_URL}/reservaciones/${id}`, {
                method: 'DELETE',
                headers: { 'Authorization': sessionStorage.getItem('authHeader') }
            })
            .then(res => {
                if (res.ok) {
                    alert('Reservación cancelada.');
                    Reservations.loadData();
                    if(document.getElementById('rooms-grid-content')) {
                        Rooms.loadData();
                    }
                }
            });
        }
    }
};