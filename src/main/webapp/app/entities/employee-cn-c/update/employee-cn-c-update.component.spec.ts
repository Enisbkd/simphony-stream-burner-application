import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { EmployeeCnCService } from '../service/employee-cn-c.service';
import { IEmployeeCnC } from '../employee-cn-c.model';
import { EmployeeCnCFormService } from './employee-cn-c-form.service';

import { EmployeeCnCUpdateComponent } from './employee-cn-c-update.component';

describe('EmployeeCnC Management Update Component', () => {
  let comp: EmployeeCnCUpdateComponent;
  let fixture: ComponentFixture<EmployeeCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeCnCFormService: EmployeeCnCFormService;
  let employeeCnCService: EmployeeCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmployeeCnCUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmployeeCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeCnCFormService = TestBed.inject(EmployeeCnCFormService);
    employeeCnCService = TestBed.inject(EmployeeCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const employeeCnC: IEmployeeCnC = { id: 11194 };

      activatedRoute.data = of({ employeeCnC });
      comp.ngOnInit();

      expect(comp.employeeCnC).toEqual(employeeCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeCnC>>();
      const employeeCnC = { id: 8307 };
      jest.spyOn(employeeCnCFormService, 'getEmployeeCnC').mockReturnValue(employeeCnC);
      jest.spyOn(employeeCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeCnC }));
      saveSubject.complete();

      // THEN
      expect(employeeCnCFormService.getEmployeeCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeCnCService.update).toHaveBeenCalledWith(expect.objectContaining(employeeCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeCnC>>();
      const employeeCnC = { id: 8307 };
      jest.spyOn(employeeCnCFormService, 'getEmployeeCnC').mockReturnValue({ id: null });
      jest.spyOn(employeeCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeCnC }));
      saveSubject.complete();

      // THEN
      expect(employeeCnCFormService.getEmployeeCnC).toHaveBeenCalled();
      expect(employeeCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeCnC>>();
      const employeeCnC = { id: 8307 };
      jest.spyOn(employeeCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
