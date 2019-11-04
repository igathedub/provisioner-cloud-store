import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedRangeDetailComponent } from 'app/entities/allocated-range/allocated-range-detail.component';
import { AllocatedRange } from 'app/shared/model/allocated-range.model';

describe('Component Tests', () => {
  describe('AllocatedRange Management Detail Component', () => {
    let comp: AllocatedRangeDetailComponent;
    let fixture: ComponentFixture<AllocatedRangeDetailComponent>;
    const route = ({ data: of({ allocatedRange: new AllocatedRange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedRangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllocatedRangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedRangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allocatedRange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
