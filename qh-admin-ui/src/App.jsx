import { HashRouter, Routes, Route, useParams } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import { ToastProvider } from './components/Toast';
import Home from './pages/Home';
import EntityPage from './pages/EntityPage';

// Keying by slug forces a full remount when the user switches entities from
// the sidebar, so per-page state (open modal, loaded rows) never leaks from
// the previously viewed entity into the newly selected one.
function EntityPageRoute() {
  const { slug } = useParams();
  return <EntityPage key={slug} />;
}

export default function App() {
  return (
    <ToastProvider>
      <HashRouter>
        <div className="app-shell">
          <Sidebar />
          <main className="app-main">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/e/:slug" element={<EntityPageRoute />} />
            </Routes>
          </main>
        </div>
      </HashRouter>
    </ToastProvider>
  );
}
