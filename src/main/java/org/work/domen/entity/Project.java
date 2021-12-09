package org.work.domen.entity;

public class Project extends AbstractEntity {
    private Segment segment;//О проекте
    private String name;//Название компании
    private String head;//руководитель проекта
    //
    private double humanAmount; ///количество человек
    private double averageSalary;//средняя з.п. сотрудника на проекте
    private double profit; //предполагаемая прибыль
    private double term;// предролагаемая продолжительность проекта(в часах)
    private double profitability; //индекс рентабельности
    //
    private ProjectMarkStatus status;


    public Project() {
    }


    public Project(String id) {
        super(id);
    }

    public Project(String id, Segment segment, String name, String head, double humanAmount, double averageSalary,
                   double profit, double term, double profitability, ProjectMarkStatus status) {
        super(id);
        this.segment = segment;
        this.name = name;
        this.head = head;
        this.humanAmount = humanAmount;
        this.averageSalary = averageSalary;
        this.profit = profit;
        this.term = term;
        this.profitability = profitability;
        this.status = status;
    }


    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public double getHumanAmount() {
        return humanAmount;
    }

    public void setHumanAmount(double humanAmount) {
        this.humanAmount = humanAmount;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public ProjectMarkStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectMarkStatus status) {
        this.status = status;
    }




}
