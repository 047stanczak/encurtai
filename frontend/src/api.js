const API_BASE = import.meta.env.VITE_API_BASE_URL;

const opts = {
  headers: { "Content-Type": "application/json" },
};

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  if (token) {
    return {
      ...opts.headers,
      "Authorization": `Bearer ${token}`,
    };
  }
  return opts.headers;
};

async function handleResponse(res) {
  let json = null;
  try {
    json = await res.json();
  } catch {
  }

  if (!res.ok) {
    const message = json?.message || "Erro na requisição";
    throw new Error(message);
  }

  return json?.data ?? json; 
}

export async function login(email, password) {
  const res = await fetch(`${API_BASE}/api/login`, {
    ...opts,
    method: "POST",
    body: JSON.stringify({ email, password }),
  });
  const result = await handleResponse(res);
  if (result && typeof result === 'string') {
    localStorage.setItem("token", result);
  }
  return result;
}

export async function register(email, password) {
  const res = await fetch(`${API_BASE}/api/register`, {
    ...opts,
    method: "POST",
    body: JSON.stringify({ email, password }),
  });
  return handleResponse(res);
}

export async function shortenUrl(originalUrl) {
  const res = await fetch(`${API_BASE}/api/url`, {
    ...opts,
    headers: getAuthHeaders(),
    method: "POST",
    body: JSON.stringify({ url: originalUrl }),
  });
  return handleResponse(res);
}

export async function getUserUrls() {
  const res = await fetch(`${API_BASE}/api/urls`, {
    ...opts,
    headers: getAuthHeaders(),
    method: "GET",
  });
  return handleResponse(res);
}

export async function logout() {
  const res = await fetch(`${API_BASE}/api/logout`, {
    ...opts,
    headers: getAuthHeaders(),
    method: "POST",
  });
  localStorage.removeItem("token");
  return handleResponse(res);
}
