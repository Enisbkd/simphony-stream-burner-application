import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrganizationLocationTransService } from '../service/organization-location-trans.service';
import { IOrganizationLocationTrans } from '../organization-location-trans.model';
import { OrganizationLocationTransFormService } from './organization-location-trans-form.service';

import { OrganizationLocationTransUpdateComponent } from './organization-location-trans-update.component';

describe('OrganizationLocationTrans Management Update Component', () => {
  let comp: OrganizationLocationTransUpdateComponent;
  let fixture: ComponentFixture<OrganizationLocationTransUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationLocationTransFormService: OrganizationLocationTransFormService;
  let organizationLocationTransService: OrganizationLocationTransService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrganizationLocationTransUpdateComponent],
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
      .overrideTemplate(OrganizationLocationTransUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationLocationTransUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationLocationTransFormService = TestBed.inject(OrganizationLocationTransFormService);
    organizationLocationTransService = TestBed.inject(OrganizationLocationTransService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const organizationLocationTrans: IOrganizationLocationTrans = { id: 9475 };

      activatedRoute.data = of({ organizationLocationTrans });
      comp.ngOnInit();

      expect(comp.organizationLocationTrans).toEqual(organizationLocationTrans);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationLocationTrans>>();
      const organizationLocationTrans = { id: 30457 };
      jest.spyOn(organizationLocationTransFormService, 'getOrganizationLocationTrans').mockReturnValue(organizationLocationTrans);
      jest.spyOn(organizationLocationTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationLocationTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationLocationTrans }));
      saveSubject.complete();

      // THEN
      expect(organizationLocationTransFormService.getOrganizationLocationTrans).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationLocationTransService.update).toHaveBeenCalledWith(expect.objectContaining(organizationLocationTrans));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationLocationTrans>>();
      const organizationLocationTrans = { id: 30457 };
      jest.spyOn(organizationLocationTransFormService, 'getOrganizationLocationTrans').mockReturnValue({ id: null });
      jest.spyOn(organizationLocationTransService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationLocationTrans: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationLocationTrans }));
      saveSubject.complete();

      // THEN
      expect(organizationLocationTransFormService.getOrganizationLocationTrans).toHaveBeenCalled();
      expect(organizationLocationTransService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationLocationTrans>>();
      const organizationLocationTrans = { id: 30457 };
      jest.spyOn(organizationLocationTransService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationLocationTrans });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationLocationTransService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
