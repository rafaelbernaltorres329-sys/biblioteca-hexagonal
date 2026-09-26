import { useState } from 'react'

const badgeClase = {
  ACTIVO: 'activo',
  VENCIDO: 'vencido',
  DEVUELTO: 'devuelto',
}

export default function Prestamos({ libros, usuarios, prestamos, onPrestar, onDevolver }) {
  const [libroId, setLibroId] = useState('')
  const [usuarioId, setUsuarioId] = useState('')
  const [error, setError] = useState('')

  const librosDisponibles = libros.filter((l) => l.disponible)

  const nombreLibro = (id) => libros.find((l) => l.id === id)?.titulo ?? `#${id}`
  const nombreUsuario = (id) => usuarios.find((u) => u.id === id)?.nombre ?? `#${id}`

  const handlePrestar = async (e) => {
    e.preventDefault()
    setError('')
    if (!libroId || !usuarioId) return
    try {
      await onPrestar(Number(libroId), Number(usuarioId))
      setLibroId('')
      setUsuarioId('')
    } catch (err) {
      setError(err.response?.data?.error ?? 'No se pudo registrar el prestamo')
    }
  }

  return (
    <div>
      <div className="card">
        <h3 style={{ marginTop: 0 }}>Registrar prestamo</h3>
        {error && <div className="error">{error}</div>}
        <form className="formulario" onSubmit={handlePrestar}>
          <select value={libroId} onChange={(e) => setLibroId(e.target.value)}>
            <option value="">Selecciona un libro</option>
            {librosDisponibles.map((l) => (
              <option key={l.id} value={l.id}>{l.titulo}</option>
            ))}
          </select>
          <select value={usuarioId} onChange={(e) => setUsuarioId(e.target.value)}>
            <option value="">Selecciona un usuario</option>
            {usuarios.map((u) => (
              <option key={u.id} value={u.id}>{u.nombre}</option>
            ))}
          </select>
          <button type="submit" className="primario" disabled={!libroId || !usuarioId}>
            Prestar
          </button>
        </form>
      </div>

      {prestamos.map((p) => (
        <div className="card fila" key={p.id}>
          <div>
            <strong>{nombreLibro(p.libroId)}</strong>
            <div style={{ color: '#666', fontSize: '0.9rem' }}>
              {nombreUsuario(p.usuarioId)} · vence {p.fechaDevolucionEsperada}
            </div>
          </div>
          <div style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
            <span className={`badge ${badgeClase[p.estado]}`}>{p.estado}</span>
            {p.estado !== 'DEVUELTO' && (
              <button onClick={() => onDevolver(p.id)}>Devolver</button>
            )}
          </div>
        </div>
      ))}
      {prestamos.length === 0 && <p>Aun no hay prestamos registrados.</p>}
    </div>
  )
}
