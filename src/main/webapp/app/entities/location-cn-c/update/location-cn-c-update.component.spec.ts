import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { LocationCnCService } from '../service/location-cn-c.service';
import { ILocationCnC } from '../location-cn-c.model';
import { LocationCnCFormService } from './location-cn-c-form.service';

import { LocationCnCUpdateComponent } from './location-cn-c-update.component';

describe('LocationCnC Management Update Component', () => {
  let comp: LocationCnCUpdateComponent;
  let fixture: ComponentFixture<LocationCnCUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let locationCnCFormService: LocationCnCFormService;
  let locationCnCService: LocationCnCService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LocationCnCUpdateComponent],
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
      .overrideTemplate(LocationCnCUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LocationCnCUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    locationCnCFormService = TestBed.inject(LocationCnCFormService);
    locationCnCService = TestBed.inject(LocationCnCService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const locationCnC: ILocationCnC = { id: 14645 };

      activatedRoute.data = of({ locationCnC });
      comp.ngOnInit();

      expect(comp.locationCnC).toEqual(locationCnC);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocationCnC>>();
      const locationCnC = { id: 3212 };
      jest.spyOn(locationCnCFormService, 'getLocationCnC').mockReturnValue(locationCnC);
      jest.spyOn(locationCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ locationCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: locationCnC }));
      saveSubject.complete();

      // THEN
      expect(locationCnCFormService.getLocationCnC).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(locationCnCService.update).toHaveBeenCalledWith(expect.objectContaining(locationCnC));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocationCnC>>();
      const locationCnC = { id: 3212 };
      jest.spyOn(locationCnCFormService, 'getLocationCnC').mockReturnValue({ id: null });
      jest.spyOn(locationCnCService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ locationCnC: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: locationCnC }));
      saveSubject.complete();

      // THEN
      expect(locationCnCFormService.getLocationCnC).toHaveBeenCalled();
      expect(locationCnCService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocationCnC>>();
      const locationCnC = { id: 3212 };
      jest.spyOn(locationCnCService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ locationCnC });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(locationCnCService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
