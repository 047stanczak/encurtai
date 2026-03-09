// src/App.jsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'
import Main from './pages/Main'
import { getUserUrls } from './api'

const ProtectedRoute = ({ children }) => {
  const [auth, setAuth] = useState(null)
  useEffect(() => {
    const checkAuth = async () => {
      try {
        await getUserUrls()
        setAuth(true)
      } catch {
        setAuth(false)
      }
    }
    checkAuth()
  }, [])

  if (auth === null) return <div>Carregando...</div>

  return auth ? children : <Navigate to="/login" replace />
}

function App() {
  return (
    <BrowserRouter basename="/encurtai">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/main"
          element={
            <ProtectedRoute>
              <Main />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  )
}

export default App