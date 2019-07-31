/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestTestModule } from '../../../test.module';
import { OrganizationProductDetailComponent } from 'app/entities/organization-product/organization-product-detail.component';
import { OrganizationProduct } from 'app/shared/model/organization-product.model';

describe('Component Tests', () => {
  describe('OrganizationProduct Management Detail Component', () => {
    let comp: OrganizationProductDetailComponent;
    let fixture: ComponentFixture<OrganizationProductDetailComponent>;
    const route = ({ data: of({ organizationProduct: new OrganizationProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestTestModule],
        declarations: [OrganizationProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrganizationProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrganizationProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.organizationProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
