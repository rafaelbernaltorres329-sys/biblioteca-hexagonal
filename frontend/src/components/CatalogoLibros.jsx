export default function CatalogoLibros({ libros }) {
  return (
    <div>
      {libros.map((libro) => (
        <div className="card fila" key={libro.id}>
          <div>
            <strong>{libro.titulo}</strong>
            <div style={{ color: '#666', fontSize: '0.9rem' }}>{libro.autor}</div>
          </div>
          <span className={`badge ${libro.disponible ? 'disponible' : 'prestado'}`}>
            {libro.disponible ? 'Disponible' : 'Prestado'}
          </span>
        </div>
      ))}
      {libros.length === 0 && <p>No hay libros registrados.</p>}
    </div>
  )
}
