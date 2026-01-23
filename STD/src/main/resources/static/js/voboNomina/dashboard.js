(() => {
  let monthlyChart = null;
  let statusChart = null;
  let dailyChart = null;
  let topEmpresasChart = null;
  let topMontoChart = null;
  let topProductosChart = null;
  let sucursalesChart = null;
  let lastMonthly = [];
  let lastStatus = [];
  const formatCurrency = (value) => {
    const num = Number(value || 0);
    return num.toLocaleString("es-MX", { style: "currency", currency: "MXN", maximumFractionDigits: 0 });
  };

  const formatPercent = (value) => `${Number(value || 0).toFixed(1)}%`;

  const setText = (id, value) => {
    const el = document.getElementById(id);
    if (el) el.textContent = value;
  };

  const initChartDefaults = () => {
    if (window.Chart && Chart.defaults) {
      Chart.defaults.color = "#64748b";
    }
  };

  const setCanvasSize = (canvas, height) => {
    if (!canvas || !canvas.parentElement) return;
    const width = canvas.parentElement.clientWidth || 600;
    canvas.width = width;
    canvas.height = height;
  };

  const renderCharts = (monthly, status, daily, topEmpresas, topMonto, topProductos, sucursales) => {
    if (monthlyChart) { monthlyChart.destroy(); monthlyChart = null; }
    if (statusChart) { statusChart.destroy(); statusChart = null; }
    if (dailyChart) { dailyChart.destroy(); dailyChart = null; }
    if (topEmpresasChart) { topEmpresasChart.destroy(); topEmpresasChart = null; }
    if (topMontoChart) { topMontoChart.destroy(); topMontoChart = null; }
    if (topProductosChart) { topProductosChart.destroy(); topProductosChart = null; }
    if (sucursalesChart) { sucursalesChart.destroy(); sucursalesChart = null; }
    const monthlyLabels = monthly.map((m) => m.mes);
    const monthlyTotal = monthly.map((m) => Number(m.total || 0));
    const monthlyAprobadas = monthly.map((m) => Number(m.aprobadas || 0));
    const monthlyRechazadas = monthly.map((m) => Number(m.rechazadas || 0));

    const monthlyCanvas = document.getElementById("chartMonthly");
    if (monthlyCanvas) {
      setCanvasSize(monthlyCanvas, 200);
      const ctxMonthly = monthlyCanvas.getContext("2d");
      monthlyChart = new Chart(ctxMonthly, {
        type: "line",
        data: {
          labels: monthlyLabels,
          datasets: [
            { label: "Total", data: monthlyTotal, borderColor: "#2563eb", backgroundColor: "rgba(37,99,235,0.15)", tension: 0.3 },
            { label: "Aprobadas", data: monthlyAprobadas, borderColor: "#16a34a", backgroundColor: "rgba(22,163,74,0.12)", tension: 0.3 },
            { label: "Rechazadas", data: monthlyRechazadas, borderColor: "#dc2626", backgroundColor: "rgba(220,38,38,0.12)", tension: 0.3 },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: { legend: { position: "bottom" } },
          scales: {
            y: { beginAtZero: true, ticks: { precision: 0 } },
          },
        },
      });
    }

    const statusLabels = status.map((s) => s.label);
    const statusValues = status.map((s) => Number(s.total || 0));
    const statusCanvas = document.getElementById("chartStatus");
    if (statusCanvas) {
      setCanvasSize(statusCanvas, 200);
      const ctxStatus = statusCanvas.getContext("2d");
      statusChart = new Chart(ctxStatus, {
        type: "doughnut",
        data: {
          labels: statusLabels,
          datasets: [
            {
              data: statusValues,
              backgroundColor: ["#2563eb", "#16a34a", "#f59e0b", "#dc2626", "#6b7280", "#0ea5e9"],
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: { legend: { position: "bottom" } },
          cutout: "60%",
        },
      });
    }

    const dailyLabels = daily.map((d) => d.dia);
    const dailyTotal = daily.map((d) => Number(d.total || 0));
    const dailyCanvas = document.getElementById("chartDaily");
    if (dailyCanvas) {
      setCanvasSize(dailyCanvas, 200);
      const ctxDaily = dailyCanvas.getContext("2d");
      dailyChart = new Chart(ctxDaily, {
        type: "line",
        data: {
          labels: dailyLabels,
          datasets: [
            { label: "Total", data: dailyTotal, borderColor: "#0ea5e9", backgroundColor: "rgba(14,165,233,0.12)", tension: 0.3 },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: { legend: { position: "bottom" } },
          scales: { y: { beginAtZero: true, ticks: { precision: 0 } } },
        },
      });
    }

    const renderBar = (canvasId, labels, values, color) => {
      const canvas = document.getElementById(canvasId);
      if (!canvas) return null;
      setCanvasSize(canvas, 200);
      const ctx = canvas.getContext("2d");
      return new Chart(ctx, {
        type: "bar",
        data: { labels, datasets: [{ label: "Total", data: values, backgroundColor: color }] },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          indexAxis: "y",
          plugins: { legend: { display: false } },
          scales: { x: { beginAtZero: true, ticks: { precision: 0 } } },
        },
      });
    };

    topEmpresasChart = renderBar(
      "chartTopEmpresas",
      topEmpresas.map((e) => e.label || "Sin empresa"),
      topEmpresas.map((e) => Number(e.total || 0)),
      "rgba(37,99,235,0.6)"
    );

    topMontoChart = renderBar(
      "chartTopMonto",
      topMonto.map((e) => e.label || "Sin empresa"),
      topMonto.map((e) => Number(e.total || 0)),
      "rgba(16,185,129,0.6)"
    );

    topProductosChart = renderBar(
      "chartTopProductos",
      topProductos.map((e) => e.label || "Sin producto"),
      topProductos.map((e) => Number(e.total || 0)),
      "rgba(245,158,11,0.6)"
    );

    sucursalesChart = renderBar(
      "chartSucursales",
      sucursales.map((s) => (s.label != null ? `Sucursal ${s.label}` : "Sucursal N/D")),
      sucursales.map((s) => Number(s.total || 0)),
      "rgba(99,102,241,0.6)"
    );
  };

  const buildUrl = (inicio, fin) => {
    const params = new URLSearchParams();
    if (inicio) params.append("fechaInicio", inicio);
    if (fin) params.append("fechaFin", fin);
    return "/gestion_vobo_nomina/dashboard/metrics?" + params.toString();
  };

  const applyData = (inicio, fin) => {
    fetch(buildUrl(inicio, fin))
      .then((res) => res.json())
      .then((data) => {
        if (data.error) return;
        const kpis = data.kpis || {};
        setText("kpiTotal", kpis.total || 0);
        setText("kpiAprobadas", kpis.aprobadas || 0);
        setText("kpiRechazadas", kpis.rechazadas || 0);
        setText("kpiProceso", kpis.enProceso || 0);
        setText("kpiMonto", formatCurrency(kpis.montoDesembolsado || 0));
        setText("kpiRespuesta", (kpis.avgDiasRespuesta || 0).toFixed(1));
        const monthly = (data.series && data.series.monthly) || [];
        const status = (data.series && data.series.status) || [];
        const daily = (data.series && data.series.daily) || [];
        const topEmpresas = (data.series && data.series.topEmpresas) || [];
        const topMonto = (data.series && data.series.topEmpresasMonto) || [];
        const topProductos = (data.series && data.series.topProductos) || [];
        const sucursales = (data.series && data.series.sucursales) || [];
        lastMonthly = monthly;
        lastStatus = status;
        window.requestAnimationFrame(() => renderCharts(monthly, status, daily, topEmpresas, topMonto, topProductos, sucursales));
        setTimeout(() => renderCharts(monthly, status, daily, topEmpresas, topMonto, topProductos, sucursales), 120);

        setText("kpiTasaAprobacion", formatPercent(kpis.tasaAprobacion || 0));
        setText("kpiTasaRechazo", formatPercent(kpis.tasaRechazo || 0));
        setText("kpiMontoPromedio", formatCurrency(kpis.avgMontoSolicitado || 0));
        setText("kpiMontoPromAprobado", formatCurrency(kpis.avgMontoAprobado || 0));
        setText("kpiSla", formatPercent(kpis.tasaSla || 0));
        setText("kpiBacklog", (kpis.backlogDiasPromedio || 0).toFixed(1));
      })
      .catch(() => {});
  };

  const init = () => {
    const inicioInput = document.getElementById("fechaInicio");
    const finInput = document.getElementById("fechaFin");
    const rangeLabel = document.getElementById("rangeLabel");
    const btnFiltrar = document.getElementById("btnFiltrar");

    const hoy = new Date();
    const hace12 = new Date();
    hace12.setMonth(hoy.getMonth() - 11);

    const toISO = (d) => d.toISOString().slice(0, 10);
    if (inicioInput && !inicioInput.value) inicioInput.value = toISO(hace12);
    if (finInput && !finInput.value) finInput.value = toISO(hoy);

    const refresh = () => {
      const inicio = inicioInput ? inicioInput.value : "";
      const fin = finInput ? finInput.value : "";
      if (rangeLabel) {
        rangeLabel.textContent = inicio && fin ? `Rango: ${inicio} a ${fin}` : "";
      }
      applyData(inicio, fin);
    };

    if (btnFiltrar) {
      btnFiltrar.addEventListener("click", refresh);
    }
    window.addEventListener("resize", () => {
      if (lastMonthly.length || lastStatus.length) {
        renderCharts(lastMonthly, lastStatus, [], [], [], [], []);
      }
    });
    initChartDefaults();
    refresh();
  };

  document.addEventListener("DOMContentLoaded", init);
})();
