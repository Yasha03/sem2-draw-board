package ru.itis.services;

import ru.itis.models.Element;
import ru.itis.repositories.ElementRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ElementServiceImpl implements ElementService{

    private ElementRepository elementRepository;

    public ElementServiceImpl(ElementRepository elementRepository){
        this.elementRepository = elementRepository;
    }

    @Override
    public void save(Element element) {
        elementRepository.save(element);
    }

    @Override
    public Element findById(UUID id) {
        Optional<Element> element = elementRepository.findById(id);
        return element.orElse(null);
    }

    @Override
    public List<Element> loadAllByBoardId(UUID id) {
        return elementRepository.loadAllByBoardId(id);
    }
}
