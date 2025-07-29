import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { GuestCheckBIService } from '../service/guest-check-bi.service';
import { IGuestCheckBI } from '../guest-check-bi.model';
import { GuestCheckBIFormService } from './guest-check-bi-form.service';

import { GuestCheckBIUpdateComponent } from './guest-check-bi-update.component';

describe('GuestCheckBI Management Update Component', () => {
  let comp: GuestCheckBIUpdateComponent;
  let fixture: ComponentFixture<GuestCheckBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let guestCheckBIFormService: GuestCheckBIFormService;
  let guestCheckBIService: GuestCheckBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [GuestCheckBIUpdateComponent],
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
      .overrideTemplate(GuestCheckBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GuestCheckBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    guestCheckBIFormService = TestBed.inject(GuestCheckBIFormService);
    guestCheckBIService = TestBed.inject(GuestCheckBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const guestCheckBI: IGuestCheckBI = { id: 12162 };

      activatedRoute.data = of({ guestCheckBI });
      comp.ngOnInit();

      expect(comp.guestCheckBI).toEqual(guestCheckBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestCheckBI>>();
      const guestCheckBI = { id: 10485 };
      jest.spyOn(guestCheckBIFormService, 'getGuestCheckBI').mockReturnValue(guestCheckBI);
      jest.spyOn(guestCheckBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestCheckBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestCheckBI }));
      saveSubject.complete();

      // THEN
      expect(guestCheckBIFormService.getGuestCheckBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(guestCheckBIService.update).toHaveBeenCalledWith(expect.objectContaining(guestCheckBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestCheckBI>>();
      const guestCheckBI = { id: 10485 };
      jest.spyOn(guestCheckBIFormService, 'getGuestCheckBI').mockReturnValue({ id: null });
      jest.spyOn(guestCheckBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestCheckBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: guestCheckBI }));
      saveSubject.complete();

      // THEN
      expect(guestCheckBIFormService.getGuestCheckBI).toHaveBeenCalled();
      expect(guestCheckBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IGuestCheckBI>>();
      const guestCheckBI = { id: 10485 };
      jest.spyOn(guestCheckBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ guestCheckBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(guestCheckBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
