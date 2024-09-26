package Scheduler;

public class VIPPatient extends Customer {
    public VIPPatient(int custID, String custName, String address, String postCode, String phoneNum, String div, int divID, String country, String age, int vip) {
        super(custID, custName, address, postCode, phoneNum, div, divID, country,age,vip);
    }
    public VIPPatient convertVIP(Customer customer){
        return new VIPPatient(customer.getCustID(),customer.getCustName(),customer.getAddress(),customer.getPostCode(),customer.getPhoneNum(),customer.getDiv(),customer.getDivID(),customer.getCountry(),customer.getAge(),customer.getVip());

    }

    @Override
    public String getCustName() {
        return "VIP-"+ super.getCustName();
    }
}
