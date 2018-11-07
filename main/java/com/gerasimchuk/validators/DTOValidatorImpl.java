package com.gerasimchuk.validators;

import com.gerasimchuk.constants.WWLConstants;
import com.gerasimchuk.dto.*;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.entities.Truck;
import org.springframework.stereotype.Service;

/**
 * Validator for Data Transfer Objects
 *
 * @author Reaper
 * @version 1.0
 */
@Service
public class DTOValidatorImpl implements DTOValidator {

    private DTOValidator currentDtoValidator;

    /**
     * Instantiates a new Dto validator.
     */
    public DTOValidatorImpl() {
    }

    /**
     * Instantiates a new Dto validator.
     *
     * @param currentDtoValidator the current dto validator
     */
    public DTOValidatorImpl(DTOValidator currentDtoValidator) {
        this.currentDtoValidator = currentDtoValidator;
    }

    public boolean validate(Object dto) {
        // todo: implement logic!!!
        if (dto == null) return false;

        if (dto instanceof AdminDTO){
            return validateAdminDTO((AdminDTO) dto);
        }

        if (dto instanceof CargoDTO){
            return validateCargoDTO((CargoDTO)dto);
        }

        if (dto instanceof CityDTO){
            return validateCityDTO((CityDTO)dto);
        }

        if (dto instanceof DriverAccountDTO){
            return validateDriverAccountDTO((DriverAccountDTO)dto);
        }

        if (dto instanceof DriverDTO){
            return validateDriverDTO((DriverDTO)dto);
        }

        if (dto instanceof IdDTO){
            return validateIdDto((IdDTO)dto);
        }

        if (dto instanceof OrderDTO){
            return validateOrderDto((OrderDTO) dto);
        }

        if (dto instanceof RouteDTO){
            return validateRouteDto((RouteDTO)dto);
        }

        if (dto instanceof TruckDTO){
            return validateTruckDto((TruckDTO)dto);
        }

        if (dto instanceof UserDTO){
            return validateUserDto((UserDTO)dto);
        }
        return true;
    }

    private boolean validateAdminDTO(AdminDTO dto){
        String id = dto.getId();
        String firstName = dto.getFirstName();
        String middleName = dto.getMiddleName();
        String lastName = dto.getLastName();
        String password = dto.getPassword();
        String personalNumber = dto.getPersonalNumber();

        boolean idValid;
        boolean firstNameValid;
        boolean middleNameValid;
        boolean lastNameValid;
        boolean passwordValid;
        boolean personalNumberValid;

        idValid = (id == null | id.length()==0 || checkId(id));
        firstNameValid = (firstName == null || firstName.length()==0 || checkName(firstName));
        middleNameValid = (middleName == null || middleName.length()==0 ||checkName(middleName));
        lastNameValid = (lastName == null || lastName.length()==0 || checkName(lastName));
        passwordValid = (password == null || password.length()== 0 ||checkPassword(password));
        personalNumberValid = (personalNumber == null || personalNumber.length()==0 || checkPersonalNumber(personalNumber));
        return idValid && firstNameValid && middleNameValid && lastNameValid && passwordValid && personalNumberValid;
    }

    private boolean validateCargoDTO(CargoDTO dto){

        String id = dto.getId();
        String personalNumber = dto.getPersonalNumber();
        String name = dto.getName();
        String weight = dto.getWeight();

        boolean idValid = (id == null || id.length()==0 || checkId(id));
        boolean  personalNumberValid = (personalNumber == null || personalNumber.length()==0 || checkPersonalNumber(personalNumber));
        boolean nameValid = (name == null || name.length()==0 || checkName(name));
        boolean weightValid = (weight == null || weight.length()==0 || checkCargoWeight(weight));
        return idValid && personalNumberValid && nameValid && weightValid;
    }

    private boolean validateCityDTO(CityDTO dto){
        String id = dto.getId();
        String name = dto.getName();
        boolean idValid;
        boolean nameValid;
        idValid = (id == null || id.length()==0 || checkId(id));
        nameValid = (name == null || name.length() ==0 || checkName(name));
        return idValid && nameValid;
    }

    private boolean validateDriverAccountDTO(DriverAccountDTO dto){
        String driverId = dto.getDriverId();
        String orderId = dto.getOrderId();
        String cargoId = dto.getCargoId();
        boolean driverIdValid;
        boolean orderIdValid;
        boolean cargoIdValid;
        driverIdValid = (driverId == null || driverId.length()==0|| checkId(driverId));
        cargoIdValid = (cargoId == null || cargoId.length()==0 || checkId(cargoId));
        orderIdValid = (orderId == null ||orderId.length()==0 || checkId(orderId));
        return driverIdValid && cargoIdValid && orderIdValid;
    }


    private boolean validateDriverDTO(DriverDTO dto){
        String id = dto.getId();
        String firstName = dto.getFirstName();
        String middleName = dto.getMiddleName();
        String lastName = dto.getLastName();
        String personalNumber = dto.getPersonalNumber();
        String password = dto.getPassword();
        String hoursWorked = dto.getHoursWorked();
        String orderId = dto.getOrderId();

        boolean idValid;
        boolean firstNameValid;
        boolean middleNameValid;
        boolean lastNameValid;
        boolean personalNumberValid;
        boolean passwordValid;
        boolean hoursWorkedValid;
        boolean orderIdValid;

        idValid = (id == null || id.length()==0|| checkId(id));
        firstNameValid = (firstName == null || firstName.length()==0 || checkName(firstName));
        middleNameValid = (middleName == null || middleName.length()==0 || checkName(middleName));
        lastNameValid = (lastName == null || lastName.length()==0 || checkName(lastName));
        personalNumberValid = (personalNumber == null || personalNumber.length()==0 || checkPersonalNumber(personalNumber));
        passwordValid = (password == null || password.length()==0 || checkPassword(password));
        hoursWorkedValid = (hoursWorked == null ||hoursWorked.length()==0 || checkHoursWorked(hoursWorked));
        orderIdValid = (orderId == null || orderId.length()==0 || checkId(orderId));
        return idValid
                && firstNameValid
                && middleNameValid
                && lastNameValid
                && personalNumberValid
                && passwordValid
                && hoursWorkedValid
                && orderIdValid;
    }

    private boolean validateIdDto(IdDTO dto){
        String id = dto.getId();
        boolean idValid = (id == null || id.length()==0 || checkId(id));
        return idValid;
    }

    private boolean validateManagerDto(ManagerDTO dto){
        // its only because managerDTO and AadminDTO has the same fields!!!
        AdminDTO adminDTO = new AdminDTO(dto.getId(),
                dto.getFirstName(),
                dto.getMiddleName(),
                dto.getLastName(),
                dto.getPassword(),
                dto.getPersonalNumber());
        return validateAdminDTO(adminDTO);
    }

    private boolean validateOrderDto(OrderDTO dto){
        String id = dto.getId();
        String pNumber = dto.getPersonalNumber();
        boolean idValid = (id == null || id.length() ==0 || checkId(id));
        boolean pNumberValid = (pNumber == null || pNumber.length() == 0 || checkPersonalNumber(pNumber));
        return idValid && pNumberValid;
    }

    private boolean validateRouteDto(RouteDTO dto){
        String id = dto.getId();
        String distance = dto.getDistance();
        boolean idValid = (id == null || id.length()==0 || checkId(id));
        boolean disatnceValid = (distance == null || distance.length()==0 || checkRouteDistance(distance));
        return idValid && disatnceValid;
    }

    private boolean validateTruckDto(TruckDTO dto){
        String id = dto.getId();
        String regNum = dto.getRegistrationNumber();
        String numOfDrivers = dto.getNumberOfDrivers();
        String capacity = dto.getCapacity();
        boolean idValid = (id == null || id.length()==0 || checkId(id));
        boolean regNumValid = (regNum == null || regNum.length()==0 || checkTruckRegistrationNumber(regNum));
        boolean numOfDriversValid = (numOfDrivers == null || numOfDrivers.length()==0 || checkNumberOfDrivers(numOfDrivers));
        boolean capacityValid = (capacity == null || capacity.length()==0 || checkTruckCapacity(capacity));
        return idValid && regNumValid&& numOfDriversValid && capacityValid;
    }

    private boolean validateUserDto(UserDTO dto){
        String id = dto.getId();
        String firstName = dto.getFirstName();
        String middleName = dto.getMiddleName();
        String lastName = dto.getLastName();
        String personalNumber = dto.getPersonalNumber();
        String password = dto.getPassword();
        String hoursWorked = dto.getHoursWorked();
        boolean idValid = (id == null || id.length()==0 || checkId(id));
        boolean firstNameValid = (firstName == null || firstName.length()==0 || checkName(firstName));
        boolean middleNameValid = (middleName == null|| middleName.length()==0 || checkName(middleName));
        boolean lastNameValid =(lastName == null || lastName.length() == 0 || checkName(lastName));
        boolean personalNumberValid = (personalNumber == null || personalNumber.length()==0 || checkPersonalNumber(personalNumber));
        boolean passwordValid = (password == null || password.length() == 0|| checkPassword(password));
        boolean hoursWorkedValid = (hoursWorked == null || hoursWorked.length() == 0|| checkHoursWorked(hoursWorked));
        return idValid && firstNameValid && middleNameValid && lastNameValid && personalNumberValid && passwordValid && hoursWorkedValid;
    }

    //////////////////////////////////////////////////////////////////////////////

        private boolean checkId(String id){
        if (id == null || id.length()==0) return false;
        int intId;
        try{
            intId = Integer.parseInt(id);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        if (intId <=0) return false;
        return true;
    }

    private boolean checkName(String name){
        if (name == null || name.length() == 0) return false;
        if (name.length() > WWLConstants.MAX_NAME_LENGTH || name.length() < WWLConstants.MIN_NAME_LENGTH) return false;
        if (!name.matches("^\\w+$")) return false;
        return true;
    }

    private boolean checkPassword(String password){
        if (password == null || password.length()== 0) return false;
        if (password.length() < WWLConstants.MIN_PASSWORD_LENGTH || password.length() > WWLConstants.MAX_PASSWORD_LENGTH) return false;
        if (password.contains(" ")) return false;
        return true;
    }

    private boolean checkPersonalNumber(String pNumber){
        if (pNumber == null || pNumber.length() ==0) return false;
        if (pNumber.length() < WWLConstants.MIN_PERSONAL_NUMBER_LENGTH || pNumber.length() > WWLConstants.MAX_PERSONAL_NUMBER_LENGTH) return false;
        return true;
    }

    private boolean checkCargoWeight(String weight){
        if (weight == null || weight.length() == 0) return false;
        double doubleWeight = 0;
        try{
            doubleWeight = Double.parseDouble(weight);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        if (doubleWeight == 0) return false;
        if (doubleWeight > WWLConstants.MAX_CARGO_WEIGHT) return false;
        return true;
    }

    private boolean checkCity(String city){
        if (city == null || city.length() == 0) return false;
        return true;
    }

    private boolean checkHoursWorked(String hoursWorked){
        if (hoursWorked == null || hoursWorked.length()==0) return false;
        double dHoursWorked = 0;
        try {
            dHoursWorked = Double.parseDouble(hoursWorked);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        if (dHoursWorked < 0) return false;
        if (dHoursWorked > WWLConstants.MAX_DRIVER_HOURS_WORKED_IN_MONTH) return false;
        return true;
    }

    private boolean checkTruckRegistrationNumber(String regNum){
        if (regNum == null || regNum.length() == 0) return false;
        if (regNum.length() > WWLConstants.TRUCK_REGISTRATION_NUMBER_LENGTH) return false;
        char[] regNumChars = regNum.toCharArray();
        boolean firstCharIsNumber = Character.isLetter(regNumChars[0]);
        boolean secondCharIsNumber = Character.isLetter(regNumChars[1]);
        if (!(firstCharIsNumber && secondCharIsNumber)) return false;
        for(int i = 2; i < 7; i++){
            if (!Character.isDigit(regNumChars[i])) return false;
        }
        return true;
    }

    private boolean checkRouteDistance(String distance){
        if (distance == null || distance.length()==0) return false;
        double dDistance = 0;
        try {
            dDistance = Double.parseDouble(distance);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        if (dDistance == 0) return false;
        if (dDistance > WWLConstants.MAX_DISTANCE_LENGTH) return false;
        return true;
    }

    private boolean checkNumberOfDrivers(String numOfDrivers){
        if (numOfDrivers == null || numOfDrivers.length() == 0) return false;
        int iNumOfDrivers = 0;
        try {
            iNumOfDrivers = Integer.parseInt(numOfDrivers);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        if (iNumOfDrivers == 0) return false;
        if (iNumOfDrivers > WWLConstants.MAX_NUMBER_OF_DRIVERS_IN_TRUCK || iNumOfDrivers < WWLConstants.MIN_NUMBER_OF_DRIVERS_IN_TRUCK) return false;
        return true;
    }

    private boolean checkTruckCapacity(String capacity){
        if (capacity == null || capacity.length() == 0) return false;
        double dCapacity = 0;
        try {
            dCapacity = Double.parseDouble(capacity);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        if (dCapacity == 0) return false;
        if (dCapacity > WWLConstants.MAX_TRUCK_CAPACITY) return false;
        return true;
    }
}
