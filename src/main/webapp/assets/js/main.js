/* =====================================================================
   StudentRent - JS dung chung (vanilla, khong phu thuoc thu vien)
   ===================================================================== */
(function () {
    'use strict';

    /** Context path cua ung dung, suy ra tu duong dan file script nay. */
    const CTX = (function () {
        const script = document.querySelector('script[src*="/assets/js/main.js"]');
        if (!script) return '';
        return script.getAttribute('src').replace(/\/assets\/js\/main\.js.*$/, '');
    })();

    const nf = new Intl.NumberFormat('vi-VN');

    // -----------------------------------------------------------------
    // Form lap hoa don: tinh so luong va thanh tien
    // -----------------------------------------------------------------
    const serviceTable = document.getElementById('serviceTable');

    function recalcRow(row) {
        const type = row.dataset.type;
        const oldIdx = parseFloat(row.querySelector('input[name="oldIndex"]')?.value) || 0;
        const newIdx = parseFloat(row.querySelector('input[name="newIndex"]')?.value) || 0;
        const qtyInput = row.querySelector('input[name="quantity"]');
        const price = parseFloat(row.querySelector('input[name="unitPrice"]')?.value) || 0;

        if (type === 'METERED') {
            // So luong = chi so cuoi - chi so dau, khong cho am
            qtyInput.value = Math.max(0, newIdx - oldIdx).toFixed(2);
        }

        const qty = parseFloat(qtyInput.value) || 0;
        const cell = row.querySelector('.amount');
        if (cell) cell.textContent = nf.format(Math.round(qty * price));
    }

    function recalcAll() {
        if (!serviceTable) return;
        serviceTable.querySelectorAll('tbody tr[data-type]').forEach(recalcRow);
    }

    if (serviceTable) {
        serviceTable.addEventListener('input', function (e) {
            const row = e.target.closest('tr[data-type]');
            if (row) recalcRow(row);
        });
        recalcAll();
    }

    // -----------------------------------------------------------------
    // Chon hop dong -> dien san tien phong + so nguoi
    // -----------------------------------------------------------------
    const contractSelect = document.getElementById('contractId');
    const roomChargeInput = document.getElementById('roomCharge');

    if (contractSelect && roomChargeInput) {
        contractSelect.addEventListener('change', function () {
            const opt = contractSelect.selectedOptions[0];
            if (opt && opt.dataset.price) {
                roomChargeInput.value = opt.dataset.price;
            }
            // Dich vu PER_PERSON lay so luong = so nguoi trong hop dong
            const occupants = parseFloat(opt?.dataset.occupants) || 1;
            serviceTable?.querySelectorAll('tr[data-type="PER_PERSON"]').forEach(function (row) {
                const qty = row.querySelector('input[name="quantity"]');
                if (qty) qty.value = occupants;
                recalcRow(row);
            });

            loadPreviousIndexes();
        });
    }

    /**
     * Nap chi so dau tu ky truoc qua AJAX.
     * TODO: bo comment khi InvoiceServlet da implement action "prepare".
     */
    function loadPreviousIndexes() {
        const contractId = contractSelect?.value;
        const month = document.getElementById('periodMonth')?.value;
        const year = document.getElementById('periodYear')?.value;
        if (!contractId || !month || !year) return;

        // const url = `${CTX}/invoices/prepare?contractId=${contractId}&month=${month}&year=${year}`;
        // fetch(url, { headers: { 'Accept': 'application/json' } })
        //     .then(r => r.ok ? r.json() : Promise.reject(r.status))
        //     .then(details => {
        //         details.forEach(d => {
        //             const row = serviceTable?.querySelector(`tr input[value="${d.serviceId}"]`)?.closest('tr');
        //             const oldIdx = row?.querySelector('input[name="oldIndex"]');
        //             if (oldIdx && d.oldIndex != null) { oldIdx.value = d.oldIndex; recalcRow(row); }
        //         });
        //     })
        //     .catch(err => console.warn('Khong nap duoc chi so ky truoc:', err));
    }

    ['periodMonth', 'periodYear'].forEach(function (id) {
        document.getElementById(id)?.addEventListener('change', loadPreviousIndexes);
    });

    // -----------------------------------------------------------------
    // Tu an alert sau 5 giay
    // -----------------------------------------------------------------
    document.querySelectorAll('.alert').forEach(function (el) {
        setTimeout(function () { el.style.display = 'none'; }, 5000);
    });
})();
