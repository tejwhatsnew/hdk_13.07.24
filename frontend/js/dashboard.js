const closeSideBarBtn = document.querySelector('#close-sidebar-btn');

closeSideBarBtn.addEventListener('click', () => {
    const mobileMenu = document.querySelector('#mobile-menu');
    mobileMenu.classList.add('hidden');
});

const openSideBarBtn = document.querySelector('#open-sidebar-btn');

openSideBarBtn.addEventListener('click', () => {
    console.log('openSideBarBtn clicked');
    const mobileMenu = document.querySelector('#mobile-menu');
    mobileMenu.classList.remove('hidden');
});