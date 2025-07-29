import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { HttpCallAuditService } from '../service/http-call-audit.service';
import { IHttpCallAudit } from '../http-call-audit.model';
import { HttpCallAuditFormService } from './http-call-audit-form.service';

import { HttpCallAuditUpdateComponent } from './http-call-audit-update.component';

describe('HttpCallAudit Management Update Component', () => {
  let comp: HttpCallAuditUpdateComponent;
  let fixture: ComponentFixture<HttpCallAuditUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let httpCallAuditFormService: HttpCallAuditFormService;
  let httpCallAuditService: HttpCallAuditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpCallAuditUpdateComponent],
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
      .overrideTemplate(HttpCallAuditUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HttpCallAuditUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    httpCallAuditFormService = TestBed.inject(HttpCallAuditFormService);
    httpCallAuditService = TestBed.inject(HttpCallAuditService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const httpCallAudit: IHttpCallAudit = { id: 15047 };

      activatedRoute.data = of({ httpCallAudit });
      comp.ngOnInit();

      expect(comp.httpCallAudit).toEqual(httpCallAudit);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHttpCallAudit>>();
      const httpCallAudit = { id: 18456 };
      jest.spyOn(httpCallAuditFormService, 'getHttpCallAudit').mockReturnValue(httpCallAudit);
      jest.spyOn(httpCallAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ httpCallAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: httpCallAudit }));
      saveSubject.complete();

      // THEN
      expect(httpCallAuditFormService.getHttpCallAudit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(httpCallAuditService.update).toHaveBeenCalledWith(expect.objectContaining(httpCallAudit));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHttpCallAudit>>();
      const httpCallAudit = { id: 18456 };
      jest.spyOn(httpCallAuditFormService, 'getHttpCallAudit').mockReturnValue({ id: null });
      jest.spyOn(httpCallAuditService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ httpCallAudit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: httpCallAudit }));
      saveSubject.complete();

      // THEN
      expect(httpCallAuditFormService.getHttpCallAudit).toHaveBeenCalled();
      expect(httpCallAuditService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHttpCallAudit>>();
      const httpCallAudit = { id: 18456 };
      jest.spyOn(httpCallAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ httpCallAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(httpCallAuditService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
