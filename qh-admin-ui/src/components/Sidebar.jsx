import { NavLink } from 'react-router-dom';
import { navigation } from '../data/meta';

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">
        <NavLink to="/" className="brand-link">
          <span className="brand-mark">قه</span>
          <span>محصول‌ساز قرض‌الحسنه</span>
        </NavLink>
      </div>
      <nav className="nav-scroll">
        {Object.entries(navigation).map(([bcKey, bc]) => (
          <div className="nav-group" key={bcKey}>
            <div className="nav-group-title">{bc.title}</div>
            {Object.entries(bc.domains).map(([domainKey, domain]) => (
              <div className="nav-domain" key={domainKey}>
                <div className="nav-domain-title">{domain.title}</div>
                {domain.entities.map((e) => (
                  <NavLink
                    key={e.slug}
                    to={`/e/${e.slug}`}
                    className={({ isActive }) => 'nav-item' + (isActive ? ' active' : '')}
                  >
                    {e.title}
                    {e.readonly && <span className="mini-badge">R</span>}
                  </NavLink>
                ))}
              </div>
            ))}
          </div>
        ))}
      </nav>
    </aside>
  );
}
