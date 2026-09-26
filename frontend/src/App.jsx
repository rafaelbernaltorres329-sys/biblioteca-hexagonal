import { useEffect, useState } from 'react'
import { obtenerLibros, obtenerUsuarios, obtenerPrestamos, crearPrestamo, devolverPrestamo } from './api'
import CatalogoLibros from './components/CatalogoLibros'
import Prestamos from './components/Prestamos'

export default function App() {
  const [tab, setTab] = useState('catalogo')
  const [libros, setLibros] = useState([])
  const [usuarios, setUsuarios] = useState([])
  const [prestamos, setPrestamos] = useState([])
  const [cargando, setCargando] = useState(true)
  const [errorConexion, setErrorConexion] = useState(false)

  const cargarDatos = async () => {
    try {
      const [l, u, p] = await Promise.all([obtenerLibros(), obtenerUsuarios(), obtenerPrestamos()])
      setLibros(l)
      setUsuarios(u)
      setPrestamos(p)
      setErrorConexion(false)
    } catch (err) {
      setErrorConexion(true)
    } finally {
      setCargando(false)
    }
  }

  useEffect(() => {
    cargarDatos()
  }, [])

  const handlePrestar = async (libroId, usuarioId) => {
    await crearPrestamo(libroId, usuarioId)
    await cargarDatos()
  }

  const handleDevolver = async (id) => {
    await devolverPrestamo(id)
    await cargarDatos()
  }

  return (
    <div className="app">
      <h1>📚 Biblioteca</h1>
      <p className="subtitulo">Sistema de gestion de prestamos — Arquitectura Hexagonal</p>

      {errorConexion && (
        <div className="error">
          No se pudo conectar con el backend en http://localhost:8080. Verifica que este corriendo.
        </div>
      )}

      <div className="tabs">
        <button className={`tab ${tab === 'catalogo' ? 'activa' : ''}`} onClick={() => setTab('catalogo')}>
          Catalogo
        </button>
        <button className={`tab ${tab === 'prestamos' ? 'activa' : ''}`} onClick={() => setTab('prestamos')}>
          Prestamos
        </button>
      </div>

      {cargando ? (
        <p>Cargando...</p>
      ) : tab === 'catalogo' ? (
        <CatalogoLibros libros={libros} />
      ) : (
        <Prestamos
          libros={libros}
          usuarios={usuarios}
          prestamos={prestamos}
          onPrestar={handlePrestar}
          onDevolver={handleDevolver}
        />
      )}
    </div>
  )
}
