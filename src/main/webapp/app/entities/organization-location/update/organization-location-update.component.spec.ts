import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { OrganizationLocationService } from '../service/organization-location.service';
import { IOrganizationLocation } from '../organization-location.model';
import { OrganizationLocationFormService } from './organization-location-form.service';

import { OrganizationLocationUpdateComponent } from './organization-location-update.component';

describe('OrganizationLocation Management Update Component', () => {
  let comp: OrganizationLocationUpdateComponent;
  let fixture: ComponentFixture<OrganizationLocationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organizationLocationFormService: OrganizationLocationFormService;
  let organizationLocationService: OrganizationLocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrganizationLocationUpdateComponent],
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
      .overrideTemplate(OrganizationLocationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganizationLocationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organizationLocationFormService = TestBed.inject(OrganizationLocationFormService);
    organizationLocationService = TestBed.inject(OrganizationLocationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const organizationLocation: IOrganizationLocation = { id: 12860 };

      activatedRoute.data = of({ organizationLocation });
      comp.ngOnInit();

      expect(comp.organizationLocation).toEqual(organizationLocation);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationLocation>>();
      const organizationLocation = { id: 18333 };
      jest.spyOn(organizationLocationFormService, 'getOrganizationLocation').mockReturnValue(organizationLocation);
      jest.spyOn(organizationLocationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationLocation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationLocation }));
      saveSubject.complete();

      // THEN
      expect(organizationLocationFormService.getOrganizationLocation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organizationLocationService.update).toHaveBeenCalledWith(expect.objectContaining(organizationLocation));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationLocation>>();
      const organizationLocation = { id: 18333 };
      jest.spyOn(organizationLocationFormService, 'getOrganizationLocation').mockReturnValue({ id: null });
      jest.spyOn(organizationLocationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationLocation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organizationLocation }));
      saveSubject.complete();

      // THEN
      expect(organizationLocationFormService.getOrganizationLocation).toHaveBeenCalled();
      expect(organizationLocationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganizationLocation>>();
      const organizationLocation = { id: 18333 };
      jest.spyOn(organizationLocationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organizationLocation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organizationLocationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
