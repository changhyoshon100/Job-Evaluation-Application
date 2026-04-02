package edu.gatech.seclass.jobcompare6300;

import android.widget.EditText;

public class JobValidator {
    public static boolean validate(EditText title,
                                   EditText company,
                                   EditText location,
                                   EditText costOfLiving,
                                   EditText salary,
                                   EditText bonus,
                                   EditText stock,
                                   EditText wellness,
                                   EditText life,
                                   EditText development) {
        boolean hasError = false;

        if (title.getText().toString().trim().isEmpty()) {
            title.setError("Title required");
            hasError = true;
        }

        if (company.getText().toString().trim().isEmpty()) {
            company.setError("Company required");
            hasError = true;
        }

        if (location.getText().toString().trim().isEmpty()) {
            location.setError("Location required");
            hasError = true;
        }

        try {
            int costOfLivingValue = Integer.parseInt(costOfLiving.getText().toString());
            if (costOfLivingValue < 0) {
                stock.setError("Must be positive integer");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            costOfLiving.setError("Cost of living required");
            hasError = true;
        }

        try {
            double salaryValue = Double.parseDouble(salary.getText().toString());
            if (salaryValue < 0) {
                stock.setError("Must be positive number");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            salary.setError("Yearly Salary required");
            hasError = true;
        }

        try {
            double bonusValue = Double.parseDouble(bonus.getText().toString());
            if (bonusValue < 0) {
                stock.setError("Must be positive number");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            bonus.setError("Yearly Bonus required");
            hasError = true;
        }

        try {
            int stockValue = Integer.parseInt(stock.getText().toString());
            if (stockValue < 0) {
                stock.setError("Must be positive integer");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            stock.setError("Stock Option Shares required");
            hasError = true;
        }

        try {
            double wellnessValue = Double.parseDouble(wellness.getText().toString());
            if (wellnessValue < 0 || wellnessValue > 1200) {
                wellness.setError("Number between 0-1200 allowed");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            wellness.setError("Wellness Stipend required");
            hasError = true;
        }

        try {
            int lifeValue = Integer.parseInt(life.getText().toString());
            if (lifeValue < 0 || lifeValue > 10) {
                life.setError("Integer between 0-10 allowed");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            life.setError("Life Insurance required");
            hasError = true;
        }

        try {
            double developmentValue = Double.parseDouble(development.getText().toString());
            if (developmentValue < 0 || developmentValue > 6000) {
                development.setError("Number between 0-6000 allowed");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            development.setError("Personal Development Fund required");
            hasError = true;
        }

        return !hasError;
    }
}
