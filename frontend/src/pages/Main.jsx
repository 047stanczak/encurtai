import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { getUserUrls, shortenUrl } from '../api'
import ShortenPopup from '../components/ShortenPopup'

function Main() {
  const [urls, setUrls] = useState([])
  const [showPopup, setShowPopup] = useState(false)
  const navigate = useNavigate()

  const shortBase = import.meta.env.VITE_SHORT_BASE_URL || import.meta.env.VITE_API_BASE_URL

  const loadUrls = async () => {
    try {
      const data = await getUserUrls()
      setUrls(Array.isArray(data) ? data : [])
    } catch (err) {
      console.error('Erro ao carregar URLs:', err)
      alert('Erro ao carregar URLs')
    }
  }

  useEffect(() => {
    loadUrls()
  }, [])

  const handleShorten = async (originalUrl) => {
    try {
      await shortenUrl(originalUrl)
      await loadUrls()
      setShowPopup(false)
    } catch (err) {
      alert(err.message)
    }
  }

  const handleLogout = async () => {
    try {
      await fetch(`${import.meta.env.VITE_API_BASE_URL}/api/logout`, {
        method: "POST",
        credentials: "include",
      })
    } catch (err) {
      console.error("Erro ao deslogar:", err)
    } finally {
      navigate("/")
    }
  }

  return (
    <div className="container">
      <div className="card glass">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2>Minhas URLs</h2>
          <button onClick={handleLogout} className="button-outline">Sair</button>
        </div>

        <button onClick={() => setShowPopup(true)} style={{ marginBottom: '1rem' }}>
          + Nova URL
        </button>

        {showPopup && (
          <ShortenPopup
            onClose={() => setShowPopup(false)}
            onShorten={handleShorten}
          />
        )}

        {urls.length === 0 ? (
          <p>Você ainda não encurtou nenhum link.</p>
        ) : (
          <ul className="url-list">
            {urls.map((url) => {
              const shortUrl = url.hash ? `${shortBase}/${url.hash}` : '#'
              return (
                <li key={url.id} className="url-item">
                  <div>
                    <strong>Original:</strong>{' '}
                    <a href={url.url} target="_blank" rel="noopener noreferrer">
                      {url.url.length > 50 ? url.url.substring(0, 50) + '...' : url.url}
                    </a>
                  </div>
                  <div>
                    <strong>Curta:</strong>{' '}
                    <a href={shortUrl} target="_blank" rel="noopener noreferrer">
                      {shortUrl}
                    </a>
                  </div>
                  <div>
                    <strong>Visualizações:</strong> {url.views}
                  </div>
                </li>
              )
            })}
          </ul>
        )}
      </div>
    </div>
  )
}

export default Main