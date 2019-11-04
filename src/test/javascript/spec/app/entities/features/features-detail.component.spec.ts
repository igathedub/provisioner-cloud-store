import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { FeaturesDetailComponent } from 'app/entities/features/features-detail.component';
import { Features } from 'app/shared/model/features.model';

describe('Component Tests', () => {
  describe('Features Management Detail Component', () => {
    let comp: FeaturesDetailComponent;
    let fixture: ComponentFixture<FeaturesDetailComponent>;
    const route = ({ data: of({ features: new Features(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [FeaturesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FeaturesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeaturesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.features).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
