import { Link } from 'react-router-dom'

function Home() {
  return (
    <div className="container">
      <div className="card glass" style={{ textAlign: 'center' }}>
        <h1>🔗 encurtai</h1>
        <p>Links curtos, simples e rápidos.</p>
        <div style={{ display: 'flex', gap: '1rem', justifyContent: 'center' }}>
          <Link to="/login" className="button">Entrar</Link>
          <Link to="/register" className="button button-outline">Criar conta</Link>
        </div>
      </div>
    </div>
  )
}

export default Home