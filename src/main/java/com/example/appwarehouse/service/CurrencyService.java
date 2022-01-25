package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Currency;
import com.example.appwarehouse.repository.CurrencyRepository;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getCurrency(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        return optionalCurrency.orElse(null);
    }

    public Result addCurrency(Currency currency) {
        Currency new_currency = new Currency();
        if (currencyRepository.existsByName(currency.getName())) {
            return new Result("Pul birligi mavjud", false);
        }
        new_currency.setName(currency.getName());
        new_currency.setActive(currency.isActive());
        currencyRepository.save(new_currency);
        return new Result("Pul birligi kiritildi", true);
    }

    public Result editCurrency(Integer id, Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            Currency editCurrency = optionalCurrency.get();
            if (currencyRepository.existsByIdIsNotAndName(id, currency.getName())) {
                return new Result("Pul birligi mavjud", false);
            }
            editCurrency.setActive(currency.isActive());
            editCurrency.setName(currency.getName());
            currencyRepository.save(editCurrency);
            return new Result("Pul birligi o'zgartirildi", true);
        }
        return new Result("Pul birligi topilmadi", false);
    }

    public Result deleteCurrency(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            currencyRepository.delete(optionalCurrency.get());
            return new Result("Pul birligi o'chirildi", true);
        }
        return new Result("Pul birligi topilmadi", false);
    }
}
