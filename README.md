# UnluMamullerApp - 2021 JAVA Android Project

-This project has been developed to track the production, sales, storage, and product logistics for bakery and pastry businesses. It provides support for both tablet and phone screens.

-The operation of the application is as follows:
The application consists of four main sections: Admin, Bench, Shipment, and Production. In the Admin section, user login information, determined and provided by the admin, allows staff to access only the designated section.

-In the Production section, the produced items are entered into the system, ensuring real-time inventory records. It also allows viewing shipment orders, enabling the separation and packaging of shipments and bench products accordingly.

-In the Shipment section, shipment personnel can view registered customers and the quantity of products they have purchased. They can update product quantities, receive payments as if making a sale, and record received payments along with the sold products. They can also view customers' past debts and total indebted product quantities.

-In the Bench section, bench personnel easily enter each sale into the system. The entered products are collected in the cart, and the system automatically calculates the total according to the price set by the system administrator. After receiving payment, the sales process is completed. Sold products are deducted from the inventory, and information about the sold products and received payments is stored in separate tables.

-The Admin panel allows for the management and viewing of bench and shipment products, personnel and customer transactions, and their adjustments. Daily, weekly, and monthly total statistics can be viewed summarily.

-RESTful architecture is used, with a database created in phpMyAdmin. Communication is facilitated through PHP web services running in the repository provided by the hosting company.

-VOLLEY library is used on the application side, and Picasso library is employed for operations related to the images stored on the server.
