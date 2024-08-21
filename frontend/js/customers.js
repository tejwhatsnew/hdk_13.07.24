const addCustomerBtn = document.getElementById('add-customer-btn');
const addCustomerCloseBtn = document.getElementById('add-customer-dialog-close-btn');

addCustomerBtn.addEventListener('click', () => {
    const addCustomerDialog = document.getElementById('add-customer-dialog');
    addCustomerDialog.classList.remove('hidden');
});

addCustomerCloseBtn.addEventListener('click', () => {
    const addCustomerDialog = document.getElementById('add-customer-dialog');
    addCustomerDialog.classList.add('hidden');
});