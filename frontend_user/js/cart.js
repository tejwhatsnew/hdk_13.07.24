let products = [];
let cart = [];
let totalPrice = 0;
let walletBalance = 10000.00;

// Fetch products from the JSON file
fetch('../assets/database/products.json')
    .then(response => response.json())
    .then(data => {
        products = data;
        // Populate cart with data from local storage
        cart = JSON.parse(localStorage.getItem('cart')) || [];
        populateCart();
    })
    .catch(error => console.error('Error fetching products:', error));

// Function to populate the cart UI
function populateCart() {
    const cartElement = document.getElementById("cart");
    cartElement.innerHTML = ""; // Clear any existing content
    totalPrice = 0; // Reset total price

    cart.forEach((cartItem, index) => {
        // Find the product information in the products array
        const product = products.find(p => p.name === cartItem.name);

        if (product) {
            // Calculate the total price for this item
            const itemTotal = product.price * cartItem.quantity;
            totalPrice += itemTotal;

            // Create the cart item HTML
            const cartItemHtml = `
            <li class="flex py-6 sm:py-10">
              <div class="flex-shrink-0">
                <img src="${product.image_url}" alt="${product.description}" class="h-24 w-24 rounded-md object-cover object-center sm:h-48 sm:w-48">
              </div>
  
              <div class="ml-4 flex flex-1 flex-col justify-between sm:ml-6">
                <div class="relative pr-9 sm:grid sm:grid-cols-2 sm:gap-x-6 sm:pr-0">
                  <div>
                    <div class="flex justify-between">
                      <h1 class="mb-5 font-bold">
                        <a href="#" class="font-medium text-2xl text-gray-900 mb-10 hover:text-gray-800">${product.name}</a>
                      </h1>
                    </div>
                    <div class="mt-2 font-bold flex text-sm text-[#4F45E4]">
                      <p class="text-gray-500">${product.category}</p>
                      <p class="ml-4 border-l border-gray-200 pl-4 text-[#4F45E4]">${cartItem.subscription}</p>
                    </div>
                    <p class="mt-1 text-sm font-medium text-gray-900 mt-8 text-xl font-semibold">₹ ${product.price.toFixed(2)}</p>
                  </div>
  
                  <div class="mt-4 sm:mt-0 sm:pr-9 w-full">
                    <label for="quantity-${index}" class="sr-only">Quantity, ${product.name}</label>
                    <select id="quantity-${index}" name="quantity-${index}" class="max-w-full rounded-md border border-gray-300 py-2 px-2 text-left text-base font-medium leading-5 text-gray-700 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500 sm:text-sm" data-index="${index}">
                      ${[...Array(10).keys()].map(i => `<option value="${i+1}" ${cartItem.quantity === i+1 ? "selected" : ""}>${i+1}</option>`).join('')}
                    </select>
  
                    <div class="absolute right-0 top-0">
                      <button type="button" class="-m-2 inline-flex p-2 text-gray-400 hover:text-gray-500" onclick="removeItem(${index})">
                        <span class="sr-only">Remove</span>
                        <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                          <path d="M6.28 5.22a.75.75 0 00-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 101.06 1.06L10 11.06l3.72 3.72a.75.75 0 101.06-1.06L11.06 10l3.72-3.72a.75.75 0 00-1.06-1.06L10 8.94 6.28 5.22z" />
                        </svg>
                      </button>
                    </div>
                  </div>
                </div>
  
                <p class="mt-4 flex space-x-2 text-sm text-gray-700">
                  <svg class="h-5 w-5 flex-shrink-0 text-green-500" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                    <path fill-rule="evenodd" d="M16.704 4.153a.75.75 0 01.143 1.052l-8 10.5a.75.75 0 01-1.127.075l-4.5-4.5a.75.75 0 011.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 011.05-.143z" clip-rule="evenodd" />
                  </svg>
                  <span>In stock</span>
                </p>
              </div>
            </li>
            `;

            // Append the HTML to the cart
            cartElement.innerHTML += cartItemHtml;
        }
    });

    // Update the total price in the order summary
    document.getElementById("orderSummary").innerText = `₹${ parseFloat(totalPrice.toFixed(2)) + 98}`;
    document.getElementById("orderSummary2").innerText = `₹${totalPrice.toFixed(2)}`;
}

// Function to remove an item from the cart
function removeItem(index) {
    cart.splice(index, 1); // Remove the item at the given index
    localStorage.setItem('cart', JSON.stringify(cart)); // Save updated cart to local storage
    populateCart(); // Re-populate the cart to reflect changes
}

// Initialize wallet balance display
function updateBalance() {
    document.getElementById('wallet-balance').textContent = `$${walletBalance.toFixed(2)}`;
}

// Handle checkout button click
document.getElementById('checkout-button').addEventListener('click', function() {
  const checkoutAmount = 200.00; // Example amount to deduct

  if (walletBalance >= totalPrice) {
      walletBalance -= (totalPrice+98);
      updateBalance();

      const button = this;
      button.classList.add('order-placed');

      // After animation ends, change button text and style
      button.addEventListener('animationend', function() {
          button.innerHTML = `
              <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 6L9 17 4 12" />
              </svg>
              Order Placed
          `;
          button.style.backgroundColor = '#4CAF50'; // Green background for success
          button.style.color = '#000'; // Black text color
      }, { once: true });
  } else {
      alert('Insufficient balance!');
  }
});

// Initial balance display
updateBalance();
