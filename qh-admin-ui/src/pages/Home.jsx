import { Link } from 'react-router-dom';
import { navigation, entities } from '../data/meta';
import { scenarios } from '../data/scenarios';
import { wizards } from '../data/wizards';
import ScenarioRunner from '../components/ScenarioRunner';
import Wizard from '../components/Wizard';

export default function Home() {
  return (
    <div className="home-page">
      <div className="hero">
        <h1>پنل مدیریت محصول‌ساز یکپارچه قرض‌الحسنه</h1>
        <p>
          این یک رابط کاربری React مستقل است که مستقیم روی REST APIهای بک‌اند Spring Boot کار می‌کند
          (از طریق پراکسی Vite به <code>localhost:8080</code>) و تمام {entities.length} موجودیت پروژه —
          هر دو Bounded Context (محصول‌ساز و اشخاص) — را با CRUD کامل پوشش می‌دهد.
        </p>
      </div>

      <section>
        <h2>نقشه موجودیت‌ها</h2>
        <div className="domain-grid">
          {Object.entries(navigation).map(([bcKey, bc]) => (
            <div className="bc-card" key={bcKey}>
              <h3>{bc.title}</h3>
              {Object.entries(bc.domains).map(([domainKey, domain]) => (
                <div key={domainKey} className="domain-block">
                  <div className="domain-block-title">{domain.title} · {domain.entities.length} موجودیت</div>
                  <div className="chip-row">
                    {domain.entities.map((e) => (
                      <Link key={e.slug} to={`/e/${e.slug}`} className="chip">
                        {e.title}
                      </Link>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          ))}
        </div>
      </section>

      <section>
        <h2>ویزارد ثبت اشخاص (Party)</h2>
        <p className="muted">
          هر ویزارد شما را قدم‌به‌قدم در یک مسیر واقعی ثبت داده (مثلاً افتتاح پرونده شخص حقیقی/حقوقی)
          راهنمایی می‌کند — در هر مرحله مقادیر را خودتان وارد می‌کنید و فقط فیلدهایی که از مرحله قبل
          مشخص شده‌اند (مثل شناسه پارتی) به‌صورت خودکار و قفل‌شده پر می‌شوند.
        </p>
        <div className="scenario-grid">
          {wizards.map((w) => (
            <Wizard key={w.id} wizard={w} />
          ))}
        </div>
      </section>

      <section>
        <h2>سناریوهای نمونه محصول‌ساز (اجرای زنده روی بک‌اند)</h2>
        <p className="muted">
          هر سناریو زنجیره واقعی از درخواست‌های POST را روی API اجرا می‌کند و رکوردهای واقعی در H2 می‌سازد؛
          پس از اجرا می‌توانید نتیجه را در جدول‌های مربوطه ببینید. (توجه: علاوه بر این سناریوها، خود بک‌اند
          از قبل برای هر جدول حداقل یک ردیف نمونه seed کرده — با مراجعه به هر صفحه از منوی راست هم
          می‌توانید مثال واقعی آن موجودیت را ببینید.)
        </p>
        <div className="scenario-grid">
          {scenarios.map((s) => (
            <ScenarioRunner key={s.id} scenario={s} />
          ))}
        </div>
      </section>
    </div>
  );
}
