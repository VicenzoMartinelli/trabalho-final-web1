package br.edu.utfpr.pb.trabalhofinalweb1.controller;

import br.edu.utfpr.pb.trabalhofinalweb1.service.impl.ServiceCrud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public abstract class CrudController<T, ID extends Serializable> {

    protected abstract ServiceCrud<T, ID> getService();

    protected abstract String getUrl();

    protected abstract void addDependenciesObjects(ModelAndView model);

    protected abstract Page<T> getCustomPaginated(Pageable pageable);

    protected abstract String getPageName();

    @GetMapping("new")
    public ModelAndView form(T entity) {
        ModelAndView md = new ModelAndView(this.getUrl() + "/form");

        Object source = null;

        try {
            source = entity == null ? ((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance() : entity;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        md.addObject("entity", source);

        addDependenciesObjects(md);

        return md;
    }

    @GetMapping("find/{id}")
    @ResponseBody
    public T edit(@PathVariable ID id) {
        return getService().findOne(id);
    }

    @PostMapping("add")
    public ResponseEntity<?> save(@Valid T entity, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        getService().save(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        try {
            this.getService().delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ModelAndView list(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);


        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);
        Optional<Page<T>> opt = Optional.ofNullable(getCustomPaginated(pageable));

        Page<T> list = opt.orElse(getService().findAll(pageable));

        ModelAndView md = new ModelAndView(this.getUrl() + "/list");

        md.addObject("list", list);
        md.addObject("pageName", getPageName());

        if (list.getTotalPages() != 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, list.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            md.addObject("pageNumbers", pageNumbers);
        }
        md.addObject("totalCount", list.getTotalElements());
        addDependenciesObjects(md);

        return md;
    }
}
