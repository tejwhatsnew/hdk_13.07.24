let products = [];
let productName, productDescription, productPrice, productImageUrl;

document.addEventListener("DOMContentLoaded", () => {
    // Fetch the products data from the JSON file
    fetch('../assets/database/products.json')
        .then(response => response.json())
        .then(data => {
            products = data; // Store the fetched data in the products variable
            console.log(products);

            // Get the product name from the URL
            const urlParams = new URLSearchParams(window.location.search);
            const productNameFromURL = urlParams.get('name');

            // Find the product by name (URL-friendly version)
            const product = products.find(p => p.name.replace(/\s+/g, '-').toLowerCase() === productNameFromURL);

            // Check if the product exists
            if (product) {
                // Store product details in variables
                productName = product.name;
                productDescription = product.description;
                productPrice = product.price;
                productImageUrl = product.image_url;

                // Insert product details into the HTML
                document.getElementById('product-title').innerText = productName;
                document.getElementById('product-description').innerText = productDescription;
                document.getElementById('product-price').innerText = '₹' + productPrice.toFixed(2);
                document.getElementById('product-image').src = productImageUrl;
                document.getElementById('product-image').alt = productName;

                const pricePerUnit = productPrice; // Set the base price per unit
                let quantity = 1;

                const quantityDisplay = document.getElementById('quantity');
                const totalPriceDisplay = document.getElementById('total-price');

                const updateTotalPrice = () => {
                    const totalPrice = pricePerUnit * quantity;
                    totalPriceDisplay.textContent = '₹' + totalPrice.toFixed(2);
                };

                document.getElementById('increase-quantity').addEventListener('click', () => {
                    quantity++;
                    quantityDisplay.textContent = quantity;
                    updateTotalPrice();
                });

                document.getElementById('decrease-quantity').addEventListener('click', () => {
                    if (quantity > 1) {
                        quantity--;
                        quantityDisplay.textContent = quantity;
                        updateTotalPrice();
                    }
                });

                // Initialize total price on load
                updateTotalPrice();

            } else {
                // If the product is not found, display a not found message
                document.getElementById('product-details').innerHTML = `<p>Product not found.</p>`;
            }

            // Get the section where the products will be inserted
            const relatedProductsSection = document.querySelector('.related-products-grid');

            // Function to get a random subset of products
            function getRandomProducts(num) {
                const shuffled = products.sort(() => 0.5 - Math.random());
                return shuffled.slice(0, num);
            }

            // Get random products (adjust the number as needed)
            const randomProducts = getRandomProducts(4);

            // Insert the products into the HTML
            randomProducts.forEach(product => {
                const productHTML = `
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
                relatedProductsSection.innerHTML += productHTML;
            });

            // Handle subscription option selection
            document.querySelectorAll('.subscription-option').forEach(label => {
                label.addEventListener('click', () => {
                    // Remove 'active' class from all labels
                    document.querySelectorAll('.subscription-option').forEach(lbl => lbl.classList.remove('active'));

                    // Add 'active' class to the selected label
                    label.classList.add('active');

                    // Ensure the associated radio input is checked
                    const radioInput = label.querySelector('input[type="radio"]');
                    radioInput.checked = true;
                });
            });

            // Add to Cart button click handler
            document.getElementById('add-to-cart').addEventListener('click', () => {
                // Ensure productPrice is defined
                if (productPrice === undefined) {
                    console.error('Product price is not defined.');
                    return;
                }

                // Get the selected subscription type
                const selectedSubscription = document.querySelector('input[name="subscription-choice"]:checked').value;

                // Get the subscription dates
                const fromDate = document.getElementById('from-date').value; // Ensure these elements exist
                const toDate = document.getElementById('to-date').value;

                // Get the quantity
                const quantity = parseInt(document.getElementById('quantity').textContent, 10) || 1;

                // Calculate the total price
                const totalPrice = productPrice * quantity;

                // Create an object representing the selected product
                const cartItem = {
                    name: productName,
                    description: productDescription,
                    price: productPrice,
                    image_url: productImageUrl,
                    subscription_type: selectedSubscription,
                    from_date: fromDate,
                    to_date: toDate,
                    quantity: quantity,
                    total_price: totalPrice
                };

                // Retrieve existing cart data from local storage or initialize an empty array
                let cart = JSON.parse(localStorage.getItem('cart')) || [];

                // Add the new cart item
                cart.push(cartItem);

                // Save the updated cart to local storage
                localStorage.setItem('cart', JSON.stringify(cart));

                // Optionally, alert the user or update the UI to show the item has been added
                alert('Item added to cart');
            });
        })
        .catch(error => console.error('Error fetching products:', error));
});
