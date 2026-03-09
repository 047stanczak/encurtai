import { useState } from 'react'

function ShortenPopup({ onClose, onShorten }) {
  const [url, setUrl] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!url) return
    setLoading(true)
    try {
      await onShorten(url)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="popup-overlay" onClick={onClose}>
      <div className="popup-card glass" onClick={(e) => e.stopPropagation()}>
        <h3>Encurtar nova URL</h3>
        <form onSubmit={handleSubmit}>
          <input
            type="url"
            placeholder="https://exemplo.com/artigo-muito-longo"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            required
            autoFocus
          />
          <div style={{ display: 'flex', gap: '0.5rem', marginTop: '1rem' }}>
            <button type="submit" disabled={loading}>
              {loading ? 'Encurtando...' : 'Encurtar'}
            </button>
            <button type="button" onClick={onClose} className="button-outline">
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default ShortenPopup