import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
})

export const obtenerLibros = () => api.get('/libros').then(r => r.data)
export const obtenerUsuarios = () => api.get('/usuarios').then(r => r.data)
export const obtenerPrestamos = () => api.get('/prestamos').then(r => r.data)
export const crearPrestamo = (libroId, usuarioId) =>
  api.post('/prestamos', { libroId, usuarioId }).then(r => r.data)
export const devolverPrestamo = (id) =>
  api.put(`/prestamos/${id}/devolver`).then(r => r.data)

export default api
