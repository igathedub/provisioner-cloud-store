import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { ElementDetailComponent } from 'app/entities/element/element-detail.component';
import { Element } from 'app/shared/model/element.model';

describe('Component Tests', () => {
  describe('Element Management Detail Component', () => {
    let comp: ElementDetailComponent;
    let fixture: ComponentFixture<ElementDetailComponent>;
    const route = ({ data: of({ element: new Element(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [ElementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ElementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.element).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
