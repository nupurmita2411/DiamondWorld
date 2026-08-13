package com.example.summer26.section1.group1.diamondworld.Turjo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataStore {

    private static final DataStore INSTANCE = new DataStore();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Path dataDir = Path.of("data");
    private List<Employee> employees = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<CustomDesignRequest> customDesignRequests = new ArrayList<>();
    private GoldPrice goldPrice = new GoldPrice();
    private List<StockReplenishmentRequest> replenishmentRequests = new ArrayList<>();
    private List<SalesTarget> salesTargets = new ArrayList<>();
    private List<Dispute> disputes = new ArrayList<>();
    private List<VaultItem> vaultItems = new ArrayList<>();
    private List<ExpenseInvoice> expenseInvoices = new ArrayList<>();
    private List<SaleTransaction> sales = new ArrayList<>();
    private List<RepairJob> repairJobs = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<StockChecklistEntry> checklistEntries = new ArrayList<>();
    private List<MonthlySalesReport> monthlyReports = new ArrayList<>();

    private DataStore() {
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    public void initialize() {
        try {
            Files.createDirectories(dataDir);
            loadAll();
            if (employees.isEmpty()) {
                seedSampleData();
                saveAll();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data store", e);
        }
    }

    private void loadAll() throws IOException {
        employees = loadList("employees.json", new TypeToken<List<Employee>>() {});
        products = loadList("products.json", new TypeToken<List<Product>>() {});
        customers = loadList("customers.json", new TypeToken<List<Customer>>() {});
        customDesignRequests = loadList("custom_designs.json", new TypeToken<List<CustomDesignRequest>>() {});
        goldPrice = loadObject("gold_price.json", GoldPrice.class, new GoldPrice());
        replenishmentRequests = loadList("replenishment.json", new TypeToken<List<StockReplenishmentRequest>>() {});
        salesTargets = loadList("sales_targets.json", new TypeToken<List<SalesTarget>>() {});
        disputes = loadList("disputes.json", new TypeToken<List<Dispute>>() {});
        vaultItems = loadList("vault_items.json", new TypeToken<List<VaultItem>>() {});
        expenseInvoices = loadList("expense_invoices.json", new TypeToken<List<ExpenseInvoice>>() {});
        sales = loadList("sales.json", new TypeToken<List<SaleTransaction>>() {});
        repairJobs = loadList("repair_jobs.json", new TypeToken<List<RepairJob>>() {});
        reservations = loadList("reservations.json", new TypeToken<List<Reservation>>() {});
        checklistEntries = loadList("checklist.json", new TypeToken<List<StockChecklistEntry>>() {});
        monthlyReports = loadList("monthly_reports.json", new TypeToken<List<MonthlySalesReport>>() {});
    }

    public void saveAll() throws IOException {
        saveList("employees.json", employees);
        saveList("products.json", products);
        saveList("customers.json", customers);
        saveList("custom_designs.json", customDesignRequests);
        saveObject("gold_price.json", goldPrice);
        saveList("replenishment.json", replenishmentRequests);
        saveList("sales_targets.json", salesTargets);
        saveList("disputes.json", disputes);
        saveList("vault_items.json", vaultItems);
        saveList("expense_invoices.json", expenseInvoices);
        saveList("sales.json", sales);
        saveList("repair_jobs.json", repairJobs);
        saveList("reservations.json", reservations);
        saveList("checklist.json", checklistEntries);
        saveList("monthly_reports.json", monthlyReports);
    }

    private <T> List<T> loadList(String file, TypeToken<List<T>> type) throws IOException {
        Path path = dataDir.resolve(file);
        if (!Files.exists(path) || Files.size(path) == 0) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            Type t = type.getType();
            List<T> list = GSON.fromJson(reader, t);
            return list != null ? list : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private <T> T loadObject(String file, Class<T> clazz, T defaultValue) throws IOException {
        Path path = dataDir.resolve(file);
        if (!Files.exists(path) || Files.size(path) == 0) {
            return defaultValue;
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            T obj = GSON.fromJson(reader, clazz);
            return obj != null ? obj : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private void saveList(String file, List<?> list) throws IOException {
        saveObject(file, list);
    }

    private void saveObject(String file, Object obj) throws IOException {
        Path path = dataDir.resolve(file);
        try (Writer writer = Files.newBufferedWriter(path)) {
            GSON.toJson(obj, writer);
        }
    }

    private void seedSampleData() {
        employees.add(new Employee("2120049", "Turjo Roy", UserRole.BRANCH_MANAGER, "bm123456"));
        employees.add(new Employee("2310973", "Nabila Bashar", UserRole.SALES_EXECUTIVE, "se123456"));
        employees.add(new Employee("2111520", "Mithila Farzana Richi", UserRole.SALES_EXECUTIVE, "se654321"));


        products.add(createProduct("P001", "DW-RING-001", "Solitaire Ring 1 Carat", "Diamond Ring",
                4.2, "VVS1", 185000, "Gulshan", "Case A-12", 3, "Available", "CERT-001"));
        products.add(createProduct("P002", "DW-NECK-002", "Diamond Necklace Classic", "Diamond Necklace",
                12.5, "VS1", 320000, "Banani", "Case B-03", 2, "Available", "CERT-002"));
        products.add(createProduct("P003", "DW-BANG-003", "Diamond Bangles Set", "Bangles",
                28.0, "VS2", 450000, "Gulshan", "Display Box C", 5, "Available", "CERT-003"));
        products.add(createProduct("P004", "DW-GOLD-004", "22K Gold Chain", "Gold",
                15.0, "N/A", 95000, "Dhanmondi", "Case C-07", 8, "Available", "N/A"));

        customers.add(createCustomer("C001", "Karim Ahmed", "01712345678", "karim@email.com", "1990123456789", 1200));
        customers.add(createCustomer("C002", "Sadia Rahman", "01898765432", "sadia@email.com", "1998765432109", 850));

        CustomDesignRequest req1 = new CustomDesignRequest();
        req1.setId("CDR-001");
        req1.setCustomerName("Karim Ahmed");
        req1.setMetalType("18K White Gold");
        req1.setRingSize("7.5");
        req1.setDiamondCut("Princess");
        req1.setStatus("Pending");
        req1.setFeasible(true);
        req1.setGemstoneEstimate(95000);
        req1.setMetalWeight(5.2);
        req1.setLaborCost(12000);
        req1.setMarkupFactor(1.25);
        customDesignRequests.add(req1);

        goldPrice.setK22(9850);
        goldPrice.setK21(9400);
        goldPrice.setK18(8100);
        goldPrice.setLastUpdated(LocalDate.now().toString());

        StockReplenishmentRequest sr1 = new StockReplenishmentRequest();
        sr1.setId("SR-001");
        sr1.setItemType("Loose Diamond");
        sr1.setClassification("Precious Stone");
        sr1.setRequestedQty(50);
        sr1.setCurrentStock(12);
        sr1.setMinThreshold(40);
        sr1.setStatus("Pending");
        replenishmentRequests.add(sr1);

        salesTargets.add(createTarget("2310973", "Nabila Bashar", 420000, 850000, 6, 2026));
        salesTargets.add(createTarget("2111520", "Mithila Farzana Richi", 380000, 750000, 6, 2026));

        Dispute d1 = new Dispute();
        d1.setId("DISP-001");
        d1.setCustomerPhone("01712345678");
        d1.setInvoiceId("INV-2026-0142");
        d1.setPurchaseDate("2026-03-15");
        d1.setCertificateNo("CERT-001");
        d1.setRepairTrail("Polishing requested 2026-04-01");
        d1.setStatus("Open");
        disputes.add(d1);

        vaultItems.add(createVault("RFID-1001", "Premium Solitaire Ring", true));
        vaultItems.add(createVault("RFID-1002", "Heritage Gold Set", true));
        vaultItems.add(createVault("RFID-1003", "Loose Diamond 2ct", false));

        ExpenseInvoice inv1 = new ExpenseInvoice();
        inv1.setId("EXP-001");
        inv1.setVendor("Dhaka Power Distribution");
        inv1.setAmount(28500);
        inv1.setType("Utility");
        inv1.setServiceTerms("Monthly electricity - SLA-2024");
        inv1.setStatus("Pending");
        expenseInvoices.add(inv1);

        RepairJob rj = new RepairJob();
        rj.setJobCardNo("JC-2026-0088");
        rj.setCustomerName("Sadia Rahman");
        rj.setItemDescription("Platinum Ring - Stone Setting");
        rj.setCurrentStage("Awaiting Stone Setting");
        rj.setLastUpdated(LocalDate.now().toString());
        repairJobs.add(rj);

        MonthlySalesReport mr = new MonthlySalesReport();
        mr.setMonth(6);
        mr.setYear(2026);
        mr.setGrossSales(2450000);
        mr.setTax(367500);
        mr.setNetProfit(820000);
        mr.setRegisterTotal(2450000);
        monthlyReports.add(mr);
    }

    private Product createProduct(String id, String barcode, String name, String category,
                                  double weight, String clarity, double price, String branch,
                                  String location, int stock, String status, String cert) {
        Product p = new Product();
        p.setId(id);
        p.setBarcode(barcode);
        p.setName(name);
        p.setCategory(category);
        p.setWeightGrams(weight);
        p.setClarity(clarity);
        p.setPrice(price);
        p.setBranch(branch);
        p.setDisplayLocation(location);
        p.setStock(stock);
        p.setStatus(status);
        p.setCertificateLink(cert);
        return p;
    }

    private Customer createCustomer(String id, String name, String phone, String email, String nid, int points) {
        Customer c = new Customer();
        c.setId(id);
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setNid(nid);
        c.setLoyaltyPoints(points);
        return c;
    }

    private SalesTarget createTarget(String empId, String name, double prev, double target, int m, int y) {
        SalesTarget st = new SalesTarget();
        st.setEmployeeId(empId);
        st.setEmployeeName(name);
        st.setPreviousSales(prev);
        st.setTargetAmount(target);
        st.setMonth(m);
        st.setYear(y);
        return st;
    }

    private VaultItem createVault(String rfid, String name, boolean inVault) {
        VaultItem v = new VaultItem();
        v.setRfidTag(rfid);
        v.setItemName(name);
        v.setInVault(inVault);
        return v;
    }

    public Optional<Employee> findEmployee(String id, String password) {
        return employees.stream()
                .filter(e -> e.getId().equals(id) && e.getPassword().equals(password))
                .findFirst();
    }

    public List<CustomDesignRequest> getPendingCustomDesigns() {
        return customDesignRequests.stream()
                .filter(r -> "Pending".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
    }

    public void approveCustomDesign(String id, double markup) throws IOException {
        for (CustomDesignRequest r : customDesignRequests) {
            if (r.getId().equals(id)) {
                r.setMarkupFactor(markup);
                r.setStatus("Approved");
                break;
            }
        }
        saveAll();
    }

    public Optional<MonthlySalesReport> getMonthlyReport(int month, int year) {
        return monthlyReports.stream()
                .filter(r -> r.getMonth() == month && r.getYear() == year)
                .findFirst();
    }

    public MonthlySalesReport calculateMonthlyReport(int month, int year) {
        double gross = sales.stream()
                .filter(s -> s.getDate() != null && s.getDate().startsWith(String.format("%04d-%02d", year, month)))
                .mapToDouble(SaleTransaction::getTotal)
                .sum();
        if (gross == 0) {
            return getMonthlyReport(month, year).orElse(null);
        }
        MonthlySalesReport report = new MonthlySalesReport();
        report.setMonth(month);
        report.setYear(year);
        report.setGrossSales(gross);
        report.setTax(gross * 0.15);
        report.setNetProfit(gross * 0.35);
        report.setRegisterTotal(gross);
        return report;
    }

    public List<StockReplenishmentRequest> getPendingReplenishments() {
        return replenishmentRequests.stream()
                .filter(r -> "Pending".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
    }

    public void authorizeReplenishment(String id) throws IOException {
        for (StockReplenishmentRequest r : replenishmentRequests) {
            if (r.getId().equals(id)) {
                r.setStatus("Authorized");
                break;
            }
        }
        saveAll();
    }

    public GoldPrice getGoldPrice() {
        return goldPrice;
    }

    public void updateGoldPrice(double k22, double k21, double k18) throws IOException {
        goldPrice.setK22(k22);
        goldPrice.setK21(k21);
        goldPrice.setK18(k18);
        goldPrice.setLastUpdated(LocalDateTime.now().format(FMT));
        for (Product p : products) {
            if ("Gold".equalsIgnoreCase(p.getCategory())) {
                p.setPrice(p.getWeightGrams() * k22 * 1.15);
            }
        }
        saveAll();
    }

    public List<SalesTarget> getSalesTargets() {
        return new ArrayList<>(salesTargets);
    }

    public void updateSalesTargets(List<SalesTarget> updated) throws IOException {
        salesTargets = new ArrayList<>(updated);
        saveAll();
    }

    public double getBranchQuota() {
        return 1600000;
    }

    public Optional<Dispute> findDispute(String phone, String invoiceId) {
        return disputes.stream()
                .filter(d -> (phone == null || phone.isBlank() || d.getCustomerPhone().equals(phone))
                        && (invoiceId == null || invoiceId.isBlank() || d.getInvoiceId().equalsIgnoreCase(invoiceId)))
                .findFirst();
    }

    public void resolveDispute(String id, String resolution) throws IOException {
        for (Dispute d : disputes) {
            if (d.getId().equals(id)) {
                d.setResolution(resolution);
                d.setStatus("Resolved");
                break;
            }
        }
        saveAll();
    }

    public List<VaultItem> getVaultManifest() {
        return new ArrayList<>(vaultItems);
    }

    public List<VaultItem> auditVaultTags(List<String> scannedTags) {
        List<VaultItem> discrepancies = new ArrayList<>();
        for (VaultItem item : vaultItems) {
            boolean scanned = scannedTags.contains(item.getRfidTag());
            if (item.isInVault() != scanned) {
                discrepancies.add(item);
            }
        }
        return discrepancies;
    }

    public List<ExpenseInvoice> getPendingExpenses() {
        return expenseInvoices.stream()
                .filter(e -> "Pending".equalsIgnoreCase(e.getStatus()))
                .collect(Collectors.toList());
    }

    public void approveExpense(String id) throws IOException {
        for (ExpenseInvoice e : expenseInvoices) {
            if (e.getId().equals(id)) {
                e.setStatus("Approved");
                break;
            }
        }
        saveAll();
    }

    public Optional<Product> findProductByBarcode(String barcode) {
        return products.stream()
                .filter(p -> p.getBarcode().equalsIgnoreCase(barcode.trim()))
                .findFirst();
    }

    public List<Product> searchProducts(String keyword) {
        String k = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(k)
                        || p.getCategory().toLowerCase().contains(k)
                        || p.getBarcode().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public Optional<Customer> findCustomerByPhone(String phone) {
        return customers.stream().filter(c -> c.getPhone().equals(phone)).findFirst();
    }

    public boolean customerExists(String phone, String nid) {
        return customers.stream()
                .anyMatch(c -> c.getPhone().equals(phone) || c.getNid().equals(nid));
    }

    public Customer registerCustomer(String name, String phone, String email, String nid) throws IOException {
        Customer c = new Customer();
        c.setId("C" + String.format("%03d", customers.size() + 1));
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setNid(nid);
        c.setLoyaltyPoints(100);
        customers.add(c);
        saveAll();
        return c;
    }

    public CustomDesignRequest createCustomOrder(String metal, String ringSize, String cut,
                                                 String customerName, double deposit) throws IOException {
        CustomDesignRequest req = new CustomDesignRequest();
        req.setId("CDR-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        req.setCustomerName(customerName);
        req.setMetalType(metal);
        req.setRingSize(ringSize);
        req.setDiamondCut(cut);
        req.setStatus("Submitted");
        req.setFeasible(true);
        req.setGemstoneEstimate(75000);
        req.setMetalWeight(4.5);
        req.setLaborCost(10000);
        req.setMarkupFactor(1.2);
        customDesignRequests.add(req);
        saveAll();
        return req;
    }

    public double calculateGoldExchangeValue(double weight, double purity) {
        double marketPrice = goldPrice.getK22();
        double meltingLoss = 0.08;
        return weight * (purity / 91.6) * marketPrice * (1 - meltingLoss);
    }

    public Reservation createReservation(String itemTag, String customerId) throws IOException {
        Optional<Product> product = products.stream()
                .filter(p -> p.getBarcode().equalsIgnoreCase(itemTag) || p.getId().equalsIgnoreCase(itemTag))
                .findFirst();
        if (product.isEmpty() || !"Available".equalsIgnoreCase(product.get().getStatus())) {
            return null;
        }
        product.get().setStatus("Reserved");
        Reservation r = new Reservation();
        r.setId("RES-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        r.setItemTag(itemTag);
        r.setCustomerId(customerId);
        r.setCustomerName(findCustomerById(customerId).map(Customer::getName).orElse("VIP Customer"));
        r.setExpiryTimestamp(LocalDateTime.now().plusHours(48).format(FMT));
        r.setStatus("Active");
        reservations.add(r);
        saveAll();
        return r;
    }

    private Optional<Customer> findCustomerById(String id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Optional<RepairJob> findRepairJob(String jobCard) {
        return repairJobs.stream()
                .filter(r -> r.getJobCardNo().equalsIgnoreCase(jobCard.trim()))
                .findFirst();
    }

    public StockChecklistEntry saveChecklist(String caseName, int physicalCount) throws IOException {
        StockChecklistEntry entry = new StockChecklistEntry();
        entry.setCaseName(caseName);
        entry.setOpeningBalance(10);
        entry.setSoldToday(2);
        entry.setPhysicalCount(physicalCount);
        entry.setExpectedCount(8);
        entry.setDiscrepancy(physicalCount - 8);
        entry.setDate(LocalDate.now().toString());
        checklistEntries.add(entry);
        saveAll();
        return entry;
    }

    public SaleTransaction processSale(String barcode, String phone, String paymentMode) throws IOException {
        Product product = findProductByBarcode(barcode).orElse(null);
        if (product == null || product.getStock() <= 0) {
            return null;
        }
        double subtotal = product.getPrice();
        double discount = 0;
        if (findCustomerByPhone(phone).isPresent()) {
            discount = subtotal * 0.05;
        }
        double tax = (subtotal - discount) * 0.05;
        double total = subtotal - discount + tax;

        SaleTransaction tx = new SaleTransaction();
        tx.setInvoiceId("INV-" + System.currentTimeMillis());
        tx.setProductBarcode(barcode);
        tx.setProductName(product.getName());
        tx.setCustomerPhone(phone);
        tx.setSubtotal(subtotal);
        tx.setDiscount(discount);
        tx.setTax(tax);
        tx.setTotal(total);
        tx.setPaymentMode(paymentMode);
        tx.setDate(LocalDate.now().toString());

        product.setStock(product.getStock() - 1);
        if (product.getStock() == 0) {
            product.setStatus("Sold");
        }
        sales.add(tx);
        saveAll();
        return tx;
    }

    public boolean verifyVendorTerms(String invoiceId) {
        return expenseInvoices.stream()
                .anyMatch(e -> e.getId().equals(invoiceId) && e.getServiceTerms() != null);
    }

    public boolean verifyReplenishmentDemand(StockReplenishmentRequest req) {
        return req.getCurrentStock() < req.getMinThreshold();
    }

    public boolean verifyBullionIndex(double k22) {
        return k22 >= 8000 && k22 <= 12000;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Dispute> getAllDisputes() {
        return new ArrayList<>(disputes);
    }

    public List<RepairJob> getAllRepairJobs() {
        return new ArrayList<>(repairJobs);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    public List<StockChecklistEntry> getAllChecklists() {
        return new ArrayList<>(checklistEntries);
    }
}




