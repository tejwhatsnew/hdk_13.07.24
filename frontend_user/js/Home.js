let products = [];

document.addEventListener('DOMContentLoaded', () => {
  // Check if the username is present in localStorage
  const storedUsername = localStorage.getItem('username');

  // If the username is not found, redirect to the login page
  if (!storedUsername) {
    window.location.href = "login.html"; // Assuming your login page is named "login.html"
    return; // Stop further execution if the user is redirected
  }

  // Fetch the products data from the JSON file
  fetch('../assets/database/products.json')
    .then(response => response.json())
    .then(data => {
      products = data; // Store the fetched data in the products variable
      
      const productContainer = document.querySelector('#products-section .grid');

      // Now that products have been fetched, populate the product cards
      products.forEach(product => {
        const productCard = `
<a href="product.html?name=${product.name.replace(/\s+/g, '-').toLowerCase()}" class="block bg-white rounded-2xl shadow-lg p-4 flex flex-col items-center" style="width: 250px; height: 400px; box-shadow: 0px 0px 40px rgba(9, 154, 203, 0.149); text-decoration: none; color: inherit;">
    <div class="p-2 w-full rounded-lg mb-4 flex items-center justify-center" style="width: 100%; height: 300px; overflow: hidden;">
        <img class="w-full h-full object-cover rounded-lg" src="${product.image_url}" alt="${product.name}">
    </div>
    <h2 class="text-lg font-semibold mb-2">${product.name}</h2>
    <p class="text-gray-600 text-sm mb-4 text-center">${product.description}</p>
    <p class="text-2xl font-bold mb-4 mt-3 text-[#4DAD24]">₹${product.price.toFixed(2)}</p>
    <button class="bg-white text-black mt-2 py-2 px-4 rounded-full border-2 flex items-center" style="border-color: #BAEA70;">
        Add to Cart
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 ms-2 w-5 mr-2" fill="#BAEA70" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13l-2 8h14M5 21a1 1 0 100-2 1 1 0 000 2zm12 0a1 1 0 100-2 1 1 0 000 2z" />
        </svg>
    </button>
</a>
        `;
        productContainer.innerHTML += productCard;
      });
    })
    .catch(error => console.error('Error fetching products:', error));
});
