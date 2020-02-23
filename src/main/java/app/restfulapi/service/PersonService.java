package app.restfulapi.service;

import app.restfulapi.domain.PersonDTO;

import java.util.ArrayList;
import java.util.List;

public class PersonService {

    private List<PersonDTO> personDTOList = new ArrayList<>();

    private PersonService() {
        this.personDTOList.add(new PersonDTO(1, "George", "Washington", false));
        this.personDTOList.add(new PersonDTO(2, "John", "Adams", false));
        this.personDTOList.add(new PersonDTO(3, "Thomas", "Jefferson", false));
    }

    private static PersonService instance;

    public static PersonService getInstance() {
        if(instance == null){
            synchronized (PersonService.class) {
                if(instance == null){
                    instance = new PersonService();
                }
            }
        }
        return instance;
    }

    public PersonDTO getPersonById(int id) {
        return this.personDTOList.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }

    public List<PersonDTO> getPersons() {
        return this.personDTOList;
    }

    public void deletePersonById(int id) {
        this.personDTOList.removeIf(p -> p.getId() == id);
    }

    public void addPerson(PersonDTO personDTO) {
        this.personDTOList.add(personDTO);
    }

    public void updatePerson(PersonDTO personDTO) {
        this.personDTOList.stream().filter(p -> p.getId() == personDTO.getId()).findFirst().ifPresent(found -> {
            found.setActive(personDTO.isActive());
            found.setFirstName(personDTO.getFirstName());
            found.setLastName(personDTO.getLastName());
        });
    }
}
