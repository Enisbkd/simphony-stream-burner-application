import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IGuestCheckBI } from 'app/entities/guest-check-bi/guest-check-bi.model';
import { GuestCheckBIService } from 'app/entities/guest-check-bi/service/guest-check-bi.service';
import { DetailLineBIService } from '../service/detail-line-bi.service';
import { IDetailLineBI } from '../detail-line-bi.model';
import { DetailLineBIFormService } from './detail-line-bi-form.service';

import { DetailLineBIUpdateComponent } from './detail-line-bi-update.component';

describe('DetailLineBI Management Update Component', () => {
  let comp: DetailLineBIUpdateComponent;
  let fixture: ComponentFixture<DetailLineBIUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detailLineBIFormService: DetailLineBIFormService;
  let detailLineBIService: DetailLineBIService;
  let guestCheckBIService: GuestCheckBIService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DetailLineBIUpdateComponent],
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
      .overrideTemplate(DetailLineBIUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetailLineBIUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detailLineBIFormService = TestBed.inject(DetailLineBIFormService);
    detailLineBIService = TestBed.inject(DetailLineBIService);
    guestCheckBIService = TestBed.inject(GuestCheckBIService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call GuestCheckBI query and add missing value', () => {
      const detailLineBI: IDetailLineBI = { id: 15187 };
      const guestCheckBI: IGuestCheckBI = { id: 10485 };
      detailLineBI.guestCheckBI = guestCheckBI;

      const guestCheckBICollection: IGuestCheckBI[] = [{ id: 10485 }];
      jest.spyOn(guestCheckBIService, 'query').mockReturnValue(of(new HttpResponse({ body: guestCheckBICollection })));
      const additionalGuestCheckBIS = [guestCheckBI];
      const expectedCollection: IGuestCheckBI[] = [...additionalGuestCheckBIS, ...guestCheckBICollection];
      jest.spyOn(guestCheckBIService, 'addGuestCheckBIToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detailLineBI });
      comp.ngOnInit();

      expect(guestCheckBIService.query).toHaveBeenCalled();
      expect(guestCheckBIService.addGuestCheckBIToCollectionIfMissing).toHaveBeenCalledWith(
        guestCheckBICollection,
        ...additionalGuestCheckBIS.map(expect.objectContaining),
      );
      expect(comp.guestCheckBISSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const detailLineBI: IDetailLineBI = { id: 15187 };
      const guestCheckBI: IGuestCheckBI = { id: 10485 };
      detailLineBI.guestCheckBI = guestCheckBI;

      activatedRoute.data = of({ detailLineBI });
      comp.ngOnInit();

      expect(comp.guestCheckBISSharedCollection).toContainEqual(guestCheckBI);
      expect(comp.detailLineBI).toEqual(detailLineBI);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetailLineBI>>();
      const detailLineBI = { id: 8196 };
      jest.spyOn(detailLineBIFormService, 'getDetailLineBI').mockReturnValue(detailLineBI);
      jest.spyOn(detailLineBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailLineBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailLineBI }));
      saveSubject.complete();

      // THEN
      expect(detailLineBIFormService.getDetailLineBI).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detailLineBIService.update).toHaveBeenCalledWith(expect.objectContaining(detailLineBI));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetailLineBI>>();
      const detailLineBI = { id: 8196 };
      jest.spyOn(detailLineBIFormService, 'getDetailLineBI').mockReturnValue({ id: null });
      jest.spyOn(detailLineBIService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailLineBI: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detailLineBI }));
      saveSubject.complete();

      // THEN
      expect(detailLineBIFormService.getDetailLineBI).toHaveBeenCalled();
      expect(detailLineBIService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetailLineBI>>();
      const detailLineBI = { id: 8196 };
      jest.spyOn(detailLineBIService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detailLineBI });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detailLineBIService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareGuestCheckBI', () => {
      it('should forward to guestCheckBIService', () => {
        const entity = { id: 10485 };
        const entity2 = { id: 12162 };
        jest.spyOn(guestCheckBIService, 'compareGuestCheckBI');
        comp.compareGuestCheckBI(entity, entity2);
        expect(guestCheckBIService.compareGuestCheckBI).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
