const API_BASE_URL = 'http://localhost:8095/api/v1';

const App = {
    showLogin: () => {
        document.getElementById('mainNav').classList.add('d-none');
        document.getElementById('app-content').innerHTML = Auth.renderLogin();
    },

    showDashboard: () => {
        document.getElementById('mainNav').classList.remove('d-none');
        document.getElementById('userDisplay').innerHTML = `<i class="bi bi-person-circle me-1 text-accent"></i> ${sessionStorage.getItem('username')}`;
        
        document.getElementById('app-content').innerHTML = `
            <div class="row mb-4">
                <div class="col-12">
                    <ul class="nav nav-pills bg-white p-2 rounded-3 border d-inline-flex" id="dashboardTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active" onclick="App.switchTab('rooms')" id="tab-rooms"><i class="bi bi-grid-1x2-fill me-2"></i>Catálogo & Reserva</button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link" onclick="App.switchTab('history')" id="tab-history"><i class="bi bi-layers-half me-2"></i>Historial General</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="tab-dynamic-content"></div>
        `;

        App.switchTab('rooms');
    },

    switchTab: (tabName) => {
        const container = document.getElementById('tab-dynamic-content');
        const btnRooms = document.getElementById('tab-rooms');
        const btnHistory = document.getElementById('tab-history');

        if(tabName === 'rooms') {
            btnRooms.classList.add('active');
            btnHistory.classList.remove('active');
            container.innerHTML = `
                <div class="row g-4">
                    <div class="col-lg-4">
                        <div id="reservation-form-container"></div>
                    </div>
                    <div class="col-lg-8">
                        <div id="rooms-table-container"></div>
                    </div>
                </div>
            `;
            Rooms.init();
            Reservations.init(); 
        } else if(tabName === 'history') {
            btnRooms.classList.remove('active');
            btnHistory.classList.add('active');
            container.innerHTML = `
                <div class="row">
                    <div class="col-12">
                        <div id="reservations-table-container"></div>
                    </div>
                </div>
            `;
            Reservations.init(); 
        }
    }
};

document.addEventListener('DOMContentLoaded', () => {
    if (sessionStorage.getItem('authHeader')) {
        App.showDashboard();
    } else {
        App.showLogin();
    }
});