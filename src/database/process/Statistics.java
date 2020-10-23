package database.process;

import database.interaction.OrderData;
import database.entity.Order;
import database.entityList.OrderList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class used to collect data over a period of time
 * @author Zixuan Zhang
 */
public class Statistics {
    private int[] income;
    private int[] orders;
    private int[] soup1;
    private int[] soup2;
    private int[] soup3;
    private int[] nori;
    private int[] chashu;
    private int[] egg;
    private int[] shoot;
    private int days = 7;
    public int[] getIncome() {
        return income;
    }

    public int[] getOrders() {
        return orders;
    }

    public int[] getSoup1() {
        return soup1;
    }

    public int[] getSoup2() {
        return soup2;
    }

    public int[] getSoup3() {
        return soup3;
    }

    public int[] getNori() {
        return nori;
    }

    public int[] getChashu() {
        return chashu;
    }

    public int[] getEgg() {
        return egg;
    }

    public int[] getShoot() {
        return shoot;
    }

    public int getDays() {
        return days;
    }

    public Statistics(){
        this(7);
    }

    public Statistics(int days){
        this.days = days;
        income = new int[days];
        orders = new int[days];
        soup1 = new int[days];
        soup2 = new int[days];
        soup3 = new int[days];
        nori = new int[days];
        chashu = new int[days];
        egg = new int[days];
        shoot = new int[days];
    }

    /**
     * Invoke the constructor (today)
     */
    public void loadData(){
        Date today = new Date();
        loadData(today);
    }

    /**
     * Collect the data in the 7 days durations from Date.
     * @param today the Date you want to collect data end with.
     */
    public void loadData(Date today){
        OrderData orderData = new OrderData();
        OrderList list = orderData.loadInfo();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order each:list.getOrders()){
            int diffDays = 0;
            try {
                diffDays = differentDays(format.parse(each.getDate()), today);
            } catch (ParseException ignored) { }
            if(diffDays < days & diffDays >= 0){
                orders[diffDays] += 1;
                income[diffDays] += each.getAmountMoney();
                switch (each.getCuisine().getSoupType()){
                    case "Tonkotsu":
                        soup1[diffDays] += 1;
                        break;
                    case "Shoyo":
                        soup2[diffDays] += 1;
                        break;
                    default:
                        soup3[diffDays] += 1;
                }
                nori[diffDays] += (each.getCuisine().isNori() ? 1 : 0) + each.getCuisine().getExtraNori();
                chashu[diffDays] += (each.getCuisine().isChashu() ? 1 : 0) + each.getCuisine().getExtraChashu();
                egg[diffDays] += (each.getCuisine().isEgg() ? 1 : 0) + each.getCuisine().getExtraEgg();
                shoot[diffDays] += each.getCuisine().getExtraShoot();
            }
        }
    }

    /**
     * the days different between date1 and date2
     * @param date1 the former day
     * @param date2 the latter day
     * @return the difference between date1 and date2
     */
    public static int differentDays(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
    }

    /**
     * Calculate the sum of the list
     * @param list int[] type list
     * @return the sum of the list
     */
    public int getSumInt(int[] list){
        int sum = 0;
        for(int each: list){
            sum += each;
        }
        return sum;
    }

    public static void main(String[] args) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day1 = "2020-05-03 16:44:23";
        String day2 = "2020-05-05 02:03:07";
        System.out.println(differentDays(format.parse(day1), format.parse(day2)));
    }

}
