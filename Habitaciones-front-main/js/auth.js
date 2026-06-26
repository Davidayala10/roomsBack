const Auth = {
    renderLogin: () => {
        return `
            <div class="row justify-content-center align-items-center min-vh-100 pt-5">
                <div class="col-md-5 col-lg-4">
                    <div class="text-center mb-4">
                        <h2 class="fw-bold tracking-tight text-dark"><i class="bi bi-building-fill-check text-primary me-2"></i>Stay</h2>
                        <p class="text-muted small">Gestión Interna de Reservaciones</p>
                    </div>
                    <div class="custom-card p-4 bg-white">
                        <h4 class="mb-3 fw-bold text-center">Iniciar Sesión</h4>
                        <form onsubmit="Auth.handleLogin(event)">
                            <div class="mb-3">
                                <label class="form-label fw-medium small text-secondary">Nombre de Usuario</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-person text-muted"></i></span>
                                    <input type="text" id="username" class="form-control border-start-0 bg-light" required placeholder="admin o alumno">
                                </div>
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-medium small text-secondary">Contraseña</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-light border-end-0"><i class="bi bi-lock text-muted"></i></span>
                                    <input type="password" id="password" class="form-control border-start-0 bg-light" required placeholder="••••••••">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-gradient-primary w-100 fw-bold py-2 shadow-sm">
                                Ingresar al Sistema <i class="bi bi-arrow-right-short ms-1"></i>
                            </button>
                        </form>
                        <div id="loginError" class="alert alert-danger mt-3 d-none text-center small rounded-3">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i> Credenciales no válidas.
                        </div>
                    </div>
                </div>
            </div>
        `;
    },

    handleLogin: (event) => {
        event.preventDefault();
        const user = document.getElementById('username').value;
        const pass = document.getElementById('password').value;
        
        const hash = btoa(`${user}:${pass}`);
        const authHeader = `Basic ${hash}`;

        fetch(`${API_BASE_URL}/cuartos`, {
            headers: { 'Authorization': authHeader }
        })
        .then(res => {
            if (res.ok) {
                sessionStorage.setItem('authHeader', authHeader);
                sessionStorage.setItem('username', user.toUpperCase());
                App.showDashboard();
            } else {
                document.getElementById('loginError').classList.remove('d-none');
            }
        })
        .catch(() => {
            document.getElementById('loginError').classList.remove('d-none');
        });
    },

    logout: () => {
        sessionStorage.clear();
        App.showLogin();
    }
};