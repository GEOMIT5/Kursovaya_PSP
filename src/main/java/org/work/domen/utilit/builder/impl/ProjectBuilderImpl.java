package org.work.domen.utilit.builder.impl;

import org.work.domen.entity.Project;
import org.work.domen.entity.ProjectMarkStatus;
import org.work.domen.entity.Segment;
import org.work.domen.utilit.builder.ProjectBuilder;



public class ProjectBuilderImpl implements ProjectBuilder {

    private String id;
    private Segment segment;
    private String name;
    private String head;
    private double humanAmount; ///выручка
    private double averageSalary;//расходы по налогу на прибыль
    private double profit; //итогоая финансовая прибыль
    private double term;// амортизация
    private double profitability; //мультипликатор ebitda
    private ProjectMarkStatus status;


    public ProjectBuilderImpl() {
        this.id = "";
    }

    public ProjectBuilderImpl(String id) {
        this.id = id;
    }

    @Override
    public ProjectBuilder withSegment(Segment segment) {
        this.segment = segment;
        return this;
    }

    @Override
    public ProjectBuilder withProjectStatus(ProjectMarkStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ProjectBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ProjectBuilder withHead(String head) {
        this.head = head;
        return this;
    }

    @Override
    public ProjectBuilder withHumanAmount(double humanAmount) {
        this.humanAmount = humanAmount;
        return this;
    }

    @Override
    public ProjectBuilder withAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
        return this;
    }

    @Override
    public ProjectBuilder withProfit(double profit) {
        this.profit = profit;
        return this;
    }

    @Override
    public ProjectBuilder withTerm(double term) {
        this.term = term;
        return this;
    }

    @Override
    public ProjectBuilder withProfitability(double profitability) {
        this.profitability = profitability;
        return this;
    }

    @Override
    public Project build() {
        Project company = new Project();

        company.setId(id);
        company.setSegment(segment);
        company.setName(name);
        company.setHead(head);
        company.setHumanAmount(humanAmount);
        company.setAverageSalary(averageSalary);
        company.setProfit(profit);
        company.setTerm(term);
        company.setProfitability(profitability);
        company.setStatus(status);

        return company;
    }


}
