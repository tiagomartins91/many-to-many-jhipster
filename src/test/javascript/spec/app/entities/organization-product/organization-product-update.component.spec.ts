/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestTestModule } from '../../../test.module';
import { OrganizationProductUpdateComponent } from 'app/entities/organization-product/organization-product-update.component';
import { OrganizationProductService } from 'app/entities/organization-product/organization-product.service';
import { OrganizationProduct } from 'app/shared/model/organization-product.model';

describe('Component Tests', () => {
  describe('OrganizationProduct Management Update Component', () => {
    let comp: OrganizationProductUpdateComponent;
    let fixture: ComponentFixture<OrganizationProductUpdateComponent>;
    let service: OrganizationProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestTestModule],
        declarations: [OrganizationProductUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrganizationProductUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganizationProductUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganizationProductService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrganizationProduct(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrganizationProduct();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
