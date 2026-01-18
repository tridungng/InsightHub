-- =========================================================
-- USERS
-- =========================================================
INSERT INTO users (id, email, password_hash, first_name, last_name, status, created_at)
VALUES ('u-admin-1', 'admin@insighthub.dev', '$2a$10$testadminhash', 'System', 'Admin', 'ACTIVE', now()),
       ('u-user-1', 'john@insighthub.dev', '$2a$10$testuserhash', 'John', 'Doe', 'ACTIVE', now()),
       ('u-user-2', 'sarah@insighthub.dev', '$2a$10$testuserhash', 'Sarah', 'Bloom', 'ACTIVE', now())
ON CONFLICT (id) DO NOTHING;

-- Roles (element collection)
INSERT INTO user_roles (user_id, role_name)
VALUES ('u-admin-1', 'ADMIN'),
       ('u-admin-1', 'USER'),
       ('u-user-1', 'USER'),
       ('u-user-2', 'USER')
ON CONFLICT (user_id, role_name) DO NOTHING;

-- Permissions (element collection)
INSERT INTO user_permissions (user_id, permission)
VALUES ('u-admin-1', 'CATALOG_READ'),
       ('u-admin-1', 'CATALOG_WRITE'),
       ('u-admin-1', 'ORDER_READ'),
       ('u-admin-1', 'ORDER_WRITE'),
       ('u-user-1', 'CATALOG_READ'),
       ('u-user-1', 'ORDER_READ'),
       ('u-user-2', 'CATALOG_READ'),
       ('u-user-2', 'ORDER_READ')
ON CONFLICT (user_id, permission) DO NOTHING;

-- =========================================================
-- CATEGORIES (self-referencing parent_id)
-- =========================================================
INSERT INTO categories (id, name, parent_id)
VALUES ('c-electronics', 'Electronics', NULL),
       ('c-computers', 'Computers', 'c-electronics'),
       ('c-phones', 'Phones', 'c-electronics'),
       ('c-home', 'Home', NULL),
       ('c-cleaning', 'Cleaning', 'c-home')
ON CONFLICT (id) DO NOTHING;

-- =========================================================
-- PRODUCTS
-- status must satisfy: ACTIVE | INACTIVE
-- =========================================================
INSERT INTO products (id, name, description, status,
                      price_amount, price_currency,
                      quantity, created_at, updated_at)
VALUES ('p-iphone-15', 'iPhone 15 Pro', 'A premium smartphone with a pro camera system.', 'ACTIVE', 1299.00, 'USD', 25,
        now() - interval '30 days', now()),
       ('p-macbook-pro', 'MacBook Pro 14"', 'High-performance laptop for developers and creators.', 'ACTIVE', 2399.00,
        'USD', 10, now() - interval '45 days', now()),
       ('p-dyson-v15', 'Dyson V15 Vacuum', 'Cordless vacuum with powerful suction.', 'ACTIVE', 699.00, 'USD', 15,
        now() - interval '20 days', now()),
       ('p-lenovo-x1', 'Lenovo X1 Carbon', 'Business ultrabook with a great keyboard.', 'ACTIVE', 1899.00, 'USD', 8,
        now() - interval '60 days', now()),
       ('p-old-router', 'Legacy Wi-Fi Router', 'Older model, kept for compatibility testing.', 'INACTIVE', 49.99, 'USD',
        0, now() - interval '365 days', now() - interval '180 days')
ON CONFLICT (id) DO NOTHING;

-- =========================================================
-- PRODUCT_CATEGORIES (join table)
-- =========================================================
INSERT INTO product_categories (product_id, category_id)
VALUES ('p-iphone-15', 'c-phones'),
       ('p-macbook-pro', 'c-computers'),
       ('p-lenovo-x1', 'c-computers'),
       ('p-dyson-v15', 'c-cleaning'),
       ('p-old-router', 'c-electronics')
ON CONFLICT (product_id, category_id) DO NOTHING;

-- =========================================================
-- ORDERS
-- status must satisfy your check constraint values:
-- PENDING | RESERVED | PAID | FULFILLING | SHIPPED | COMPLETED | CANCELLED
-- payment_status: UNPAID | AUTHORIZED | CAPTURED | REFUNDED | FAILED
-- =========================================================
INSERT INTO order_headers (id, user_id, status, payment_status, created_at)
VALUES ('o-001', 'u-user-1', 'PENDING', 'UNPAID', now() - interval '2 days'),
       ('o-002', 'u-user-1', 'PAID', 'CAPTURED', now() - interval '1 day'),
       ('o-003', 'u-user-2', 'SHIPPED', 'CAPTURED', now() - interval '5 hours')
ON CONFLICT (id) DO NOTHING;

-- =========================================================
-- ORDER ITEMS
-- We avoid inserting duplicates by checking existence of the same line item
-- =========================================================
INSERT INTO order_items (order_id, product_id, name, unit_amount, currency, quantity)
SELECT *
FROM (VALUES ('o-001', 'p-iphone-15', 'iPhone 15 Pro', 1299.00::numeric(38, 2), 'USD', 1),
             ('o-002', 'p-macbook-pro', 'MacBook Pro 14"', 2399.00::numeric(38, 2), 'USD', 1),
             ('o-002', 'p-dyson-v15', 'Dyson V15 Vacuum', 699.00::numeric(38, 2), 'USD', 1),
             ('o-003', 'p-lenovo-x1', 'Lenovo X1 Carbon', 1899.00::numeric(38, 2), 'USD',
              1)) AS v(order_id, product_id, name, unit_amount, currency, quantity)
WHERE NOT EXISTS (SELECT 1
                  FROM order_items oi
                  WHERE oi.order_id = v.order_id
                    AND oi.product_id = v.product_id
                    AND oi.unit_amount = v.unit_amount
                    AND oi.currency = v.currency
                    AND oi.quantity = v.quantity);
