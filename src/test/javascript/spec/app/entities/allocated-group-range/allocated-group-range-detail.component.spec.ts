import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedGroupRangeDetailComponent } from 'app/entities/allocated-group-range/allocated-group-range-detail.component';
import { AllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';

describe('Component Tests', () => {
  describe('AllocatedGroupRange Management Detail Component', () => {
    let comp: AllocatedGroupRangeDetailComponent;
    let fixture: ComponentFixture<AllocatedGroupRangeDetailComponent>;
    const route = ({ data: of({ allocatedGroupRange: new AllocatedGroupRange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedGroupRangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllocatedGroupRangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedGroupRangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allocatedGroupRange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
